#include "mike_protocol.h"

int main(int argc, char *argv[]) {

	int sockdes;
	struct addrinfo	hints, *p, *res;

	/* Set up hints with our basic info */
	memset(&hints, 0, sizeof hints);
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;

	/* Start getting the address info for the socket */
	getaddrinfo( argv[1], PORT, &hints, &res );
	/* Find the valid entry */
	for (p = res; p != NULL; p = p->ai_next) {
		if (p->ai_family = AF_INET) { /* Got IPv4 info! */
			break;
		}
	}
	// freeaddrinfo(res);
	res = NULL;

	/* Set up our socket */
	if ((sockdes = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) == -1){
		perror("client--socket");
		exit(1);
	}

	/* Begin server connection */
	if (connect( sockdes, p->ai_addr, p->ai_addrlen ) == -1) {
		perror("client--connection error");
		exit(1);
	}

	/* Introduce ourselves */
	char helo[] = "helo";
	int len = strlen(helo);
	int bytes_sent = 0;
	while ( bytes_sent < len ) {
		bytes_sent += send( sockdes, helo, len, 0 );
	}

	/* Get reply */
	char buffer[4096];
	memset(buffer, '\0', 4096);
	int bytes_recieved = recv(sockdes, buffer, 256, 0);
	printf("%s\n", buffer);

	char command[5];
	char arg[256];
	memset(command, '\0', 5);
	while ((strcmp(command, "EXIT") != 0) && (strcmp(command,"exit") != 0 )) {

		printf("> ");
		fgets(buffer, 4096, stdin);
		sscanf(buffer, "%s %s", command, arg);
		send_mesg(sockdes, command, 4);

		if ( (command[0] == 'E') || (command[0] == 'e')) {
			char * response = recv_mesg(sockdes);
			printf("%s\n", response);
			free(response);
			break;
		}
		else if ( (command[0] == 'L') || (command[0] == 'l')) {

			printf("Sending path: %s\n",arg);
			send_mesg(sockdes, arg, strlen(arg)); 
			printf("sent path\n");
			char * response = recv_mesg(sockdes);
			printf("result length: %d\n", strlen(response));
			puts(response);
			free(response);

		}
		else if ((strcmp(command, "RETR") == 0) || (strcmp(command,"retr") == 0 )) {

			printf("to be implemented\n");

		}
		else if ( (command[0] == 'S') || (command[0] == 's')) {

			
			int in;
			if ((in = open(arg, O_RDONLY)) < 0) {
				perror("client cannot open file");
				continue;
			}

			send_mesg(sockdes, arg, strlen(arg));
			char * status= recv_mesg(sockdes);
			if (status[0] != '0') {
				perror("server couldn't do that thing with the file");
				continue;
			}
			printf("Server opened file.\n");
			free(status);

			struct stat fileinfo;
			fstat(in, &fileinfo);
			// printf("filesize: %8d\n", fileinfo.st_size);
			int f_size = fileinfo.st_size;
			char size_buff[10];
			sprintf(size_buff, "%d", f_size);
			send_mesg(sockdes, size_buff, 10);
			char buff[512];
			int bytes;
			while ((bytes = read(in, buff, 512)) > 0) {
				// do error checking
				printf("Trying to send %d bytes\n", bytes);
				send(sockdes, buff, bytes, 0);
				memset(buff, '\0', 512);
			}
			/*
			int i;
			for (i = 0; i < f_size; i++) {
				char temp = fgetc(in);
				send(sockdes, temp, sizeof(char), 0);
			}
			*/

		}
		else if ( (command[0] == 'C') || (command[0] == 'c')) {

			send_mesg(sockdes, arg, strlen(arg)); 
			char * response = recv_mesg(sockdes);
			printf("%s\n", response);
			free(response);

		}
		else {
			printf("command not supported\n");
			
		}
		
	}

	return 0;

}
