#include "mike_protocol.h"

int main(int argc, char *argv[]) {

	int sockdes;
	struct addrinfo	hints, *p, *res;

	/* Set up hints with our basic info */
	memset(&hints, 0, sizeof(hints));
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	// hints.ai_flags = AI_PASSIVE; /* Bind to local IP addr */

	/* Start getting the address info for the socket */
	getaddrinfo( "localhost", PORT, &hints, &res );
	/* Find the valid entry */
	for (p = res; p != NULL; p = p->ai_next) {
		if (p->ai_family == AF_INET) { /* Got IPv4 info! */
			break;
		}
	}
	void *addr;
	char ipstr[INET_ADDRSTRLEN];
	struct sockaddr_in *ipv4 = (struct sockaddr_in *)p->ai_addr;
	addr = &(ipv4->sin_addr);
	inet_ntop(p->ai_family, addr, ipstr, sizeof ipstr);
	printf( "Server address: %s\n", ipstr );

	/* Set up our socket, bind to our PORT, and listen() */
	if ((sockdes = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) == -1){
		perror("server socket");
		exit(1);
	}
	int yes = 1;
	if (setsockopt(sockdes, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1) {
		perror("setsockopt");
		exit(1);
	}
	if (bind( sockdes, p->ai_addr, p->ai_addrlen ) == -1) {
		perror("server bind");
		exit(1);
	}
	if (listen(sockdes, MAX_BACKLOG) == -1) {
		perror("listen");
		exit(1);
	} /* Should be up and listening by now */
	printf("Server startup successful!\n");

	/* Accept incoming connection */
	struct sockaddr_storage in_addr;
	socklen_t in_size = sizeof(in_addr);
	int client;
	client = accept( sockdes, (struct sockaddr *)&in_addr, &in_size );

	/* Now that there is a connection, we can stop listening */
	close(sockdes);

	/* Wait for proper introduction */
	char helo[256];
	char *nohelo = "Improper introduction!";
	int nohelo_len = strlen(nohelo);
	int bytes_recv = recv( client, helo, 4, 0);
	/*
	if ( bytes > 4 ) {
		perror("server: too many bytes for helo");
		send(client, nohelo, nohelo_len, 0);
		close(client);
		exit(0);
	}
	*/
	if ( !strcmp( helo, "helo" )) {
		perror("server: no helo!");
		send(client, nohelo, nohelo_len, 0);
		close(client);
		exit(0);
	}

	helo[bytes_recv] = '\0';
	printf("Server recieved response %s\n", helo);

	/* Create and send welcome message */
	char cli_addr[INET_ADDRSTRLEN];
	// struct sockaddr_in *ipv4 = (struct sockaddr_in *)p->ai_addr;
	inet_ntop(p->ai_family, &(ipv4->sin_addr), cli_addr, INET_ADDRSTRLEN);
	char welcome[] = "Welcome ";
	strcat(welcome, cli_addr);
	int len = strlen(welcome);
	int bytes_sent = 0;
	while (bytes_sent < len)
		bytes_sent += send(client, welcome, len, 0);

	//char command[5];
	char * command;
	while ((strcmp(command, "EXIT") != 0) && (strcmp(command,"exit") != 0 )) {

		short nmsg_len, hmsg_len;
		command = recv_mesg(client);
		// printf("Command is to %s.\n", command);
		if ( (command[0] == 'E') || (command[0] == 'e')) {
			char qmesg[] = "Server disconnecting client";
			short hmesg_len = strlen(qmesg);
			short nmesg_len = htons(hmesg_len);
			bytes_sent = 0;
			while (bytes_sent < sizeof(short))
				send(client, &nmesg_len, sizeof(short), 0);
			bytes_sent = 0;
			while (bytes_sent < hmesg_len)
				send(client, qmesg, hmesg_len, 0);
			free(command);
			break;
		}
		else if ( (command[0] == 'L') || (command[0] == 'l')) {

			printf("Server received LIST command.\n");
			char * path = recv_mesg(client);
			/*
			printf("Path: %s\n", path);
			bytes_recv = 0;
			len = sizeof(short);
			short nmesg_len;
			while ( bytes_recv < len )
				bytes_recv += recv(client, &nmesg_len, len, 0);
			short hmesg_len = ntohs(nmesg_len);

			char path[hmsg_len + 1];
			bytes_recv = 0;
			while ( bytes_recv < hmsg_len )
				bytes_recv += recv( client, path, hmsg_len, 0 );
			path[hmesg_len] = '\0';

			char * pbuffer = list (path, 4096);
			hmesg_len = strlen(pbuffer);
			nmesg_len = htons(hmesg_len);

			send_mesg(client, pbuffer, 4096);
			/*
			bytes_sent = 0;
			len = sizeof(short);
			while ( bytes_sent < len )
				bytes_sent += send(client, &nmesg_len, len, 0);
			bytes_sent = 0;
			while (bytes_sent < hmesg_len)
				bytes_sent += send(client, pbuffer, hmesg_len, 0);
				*/
			free(pbuffer);

		}
		else if ((strcmp(command, "RETR") == 0) || (strcmp(command,"retr") == 0 )) {

			printf("to be implemented\n");

		}
		else if ((strcmp(command, "STOR") == 0) || (strcmp(command,"stor") == 0 )) {

			printf("to be implemented\n");

		}
		else if ((strcmp(command, "CD") == 0) || (strcmp(command,"cd") == 0 )) {

			printf("to be implemented\n");

		}
		else {
			printf("command not supported\n");
			
		}
		
		free(command);
	}
	/* All done for now, clean up and close */
	freeaddrinfo(res);

	exit(0);
}
