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

	/*
	 * Main control loop
	 */
	//char command[5];
	char * command;
	while ((strcmp(command, "EXIT") != 0) && (strcmp(command,"exit") != 0 )) {

		short nmsg_len, hmsg_len;
		command = recv_mesg(client);
		// printf("Command is to %s.\n", command);
		switch (command[0]) {
			/*
			 * EXIT
			 * Disconnect client
			 */
			case 'E': case 'e':
				{
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
				}

				break;

			/*
			 * LS, or LIST
			 * List all files in DIR
			 */
			case 'L':
			case 'l':
				{
					printf("Server received LIST command.\n");
					char * path = recv_mesg(client);
					printf("Path: %s\n", path);
					char * result = list(path, 4096);
					puts(result);
					send_mesg(client, result, strlen(result));
					free(path);
					free(result);
				}

				break;
				
			/*
			 * RETR or Retrieve
			 * get a file from the server
			 */
			case 'R':
			case 'r':
				{
					printf("Server got RETR command\n");
					char * f_name = recv_mesg(client);

					int in;
					if ((in = open(f_name, O_RDONLY)) < 0) {
						perror("server cannot open file");
						send_mesg(client, "-1", 2);
						continue;
					}
					send_mesg(client, "0", 1);
					printf("Server opened file.\n");
					char * status= recv_mesg(client);
					if (status[0] != '0') {
						perror("client couldn't do that thing with the file");
						continue;
					}
					printf("Client opened file.\n");
					struct stat fileinfo;
					fstat(in, &fileinfo);
					int f_size = fileinfo.st_size;
					char * size_buff = malloc(sizeof(char) * 10);
					sprintf(size_buff, "%d", f_size);
					send_mesg(client, size_buff, 10);
					char * buff = malloc(sizeof(char) * 512);
					int bytes;
					while ((bytes = read(in, buff, 512)) > 0) {
						printf("Trying to send %d bytes\n", bytes);
						send(client, buff, bytes, 0);
						memset(buff, '\0', 512);
					}
					free(size_buff);
					free(buff);
				}

				break;

			/*
			 * ST or STOR
			 * Store or put a file on the server in DIR
			 */
			case 'S':
			case 's':
				{
					printf("Server got STOR command\n");
					char * f_name = recv_mesg(client);
					printf("Opening %s\n", f_name);
					int out;
					if ((out = open(f_name, O_WRONLY | O_CREAT | O_EXCL, 0644)) < 0) {
						perror("server cannot open file");
						send_mesg(client, "-1", 2);
						continue;
					}
					send_mesg(client, "0", 1);
					char * size_buff = recv_mesg(client);
					int f_size = atoi(size_buff);
					free(size_buff);
					char *buff = malloc(sizeof(char) * 512);
					int bytes_recv = 0;
					while (bytes_recv < f_size) {
						memset(buff, '\0', 512);
						bytes_recv += recv(client, buff, 512, 0);
						printf("got %d bytes\n", bytes_recv);
						write(out, buff, strlen(buff));
					}
					printf("Done recieving file\n");

					free(buff);
					close(out);
				}

				break;

			/*
			 * CD or CHDIR
			 * Switch to new director DIR
			 */
			case 'C':
			case 'c':
				{
					printf("Server received CD command.\n");
					char * path = recv_mesg(client);
					printf("Path: %s\n", path);
					if (chdir(path) != 0) {
						char *error = "Path not changed.\n";
						perror(error);
						send_mesg(client, error, strlen(error));
					} else {
						char *success ="Path changed.\n";
						send_mesg(client, success, strlen(success));
					}
					free(path);
				}

				break;

			default:
				printf("command not supported\n");
				break;
		}
		
		free(command);
	}
	/* All done for now, clean up and close */
	freeaddrinfo(res);

	exit(0);
}
