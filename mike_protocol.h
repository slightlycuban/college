#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include <limits.h>
#include <unistd.h>
#include <fcntl.h>

#define PORT "41910"
#define MAX_BACKLOG 5

typedef struct filelist {
	struct filelist * next;
	char * path;
} flist;

flist * create_list(char * path) {
	flist * newnode = malloc(sizeof(flist));
	newnode->path = path;
	newnode->next = NULL;
}

flist * add_path(flist * files, char * path) {
	flist * head = files;
	if (head == NULL) {
		create_list(path);
		return head;
	}

	while (files->next != NULL)
		files = files->next;
	files->next = create_list(path);

	return head;
}



flist * fsearch (char fname[], char path[], flist* current) {
	DIR *dp;
	struct dirent *dirp;
	struct stat ibuffer;

	if ((dp = opendir(path)) == NULL) {
		perror("cannot open directory");
		return current;
	}
	while ((dirp = readdir(dp)) != NULL) {
		if (dirp->d_name[0] == '.')	/* Ignore hidden/dot files */
			continue;

		if (stat(dirp->d_name, &ibuffer) < 0) /* Some stat error */
			exit(4);

		if (S_ISDIR(ibuffer.st_mode) == 0) {
			fsearch(fname, strcat(path,dirp->d_name), current);
		}
		else {
			if (strcmp(dirp->d_name, fname)==0) {
				char * temp = malloc(sizeof(dirp->d_name) + sizeof(path) + 1);
				strcpy(temp, path);
				strcat(temp, "/");
				strcat(temp, dirp->d_name);
				current = add_path(current, temp);
			} else continue;
		}
	}
}	


char * list ( char * path, int buffsize ) {

    struct stat	    buffer;
    lstat(path, &buffer);
    char * rvalue = malloc(sizeof(char) * buffsize);

    if (S_ISDIR(buffer.st_mode) == 0) { // This section is for an individual file
	    strcat(rvalue, path);
	    strcat(rvalue, "\n");
    }
    else {
		DIR		*dp;
		struct dirent	*dirp;
		struct stat	ibuffer;

		if ((dp = opendir(path)) == NULL)
			perror("cannot open directory");

		while ((dirp = readdir(dp)) != NULL) {
	    		if (dirp->d_name[0] == '.')	/* Ignore hidden/dot files */
		    		continue;

			char name[100]; /* Change to MAX_FILENAME in limits */
			strcpy(name, path);	/* Copy and append */
			strcat(name,"/");
			strcat(name,dirp->d_name);	/* Path to name */
			if (lstat(name, &ibuffer) < 0) /* Some stat error */
				exit(4);

			if (S_ISDIR(ibuffer.st_mode) == 0) {
				strcat(rvalue, dirp->d_name);
				strcat(rvalue, "\n");
			}
			else {
				strcat(rvalue, dirp->d_name);
				strcat(rvalue, "/\n");
			}
		}
    }
    return rvalue;
}

void send_mesg(int server, char message[], int length) {
	short hmesg_len = length;
	short nmesg_len = htons(hmesg_len);
	short bytes_sent = 0;
	while (bytes_sent < sizeof(short))
		bytes_sent += send(server, &nmesg_len, sizeof(short), 0);
	bytes_sent = 0;
	while (bytes_sent < hmesg_len)
		bytes_sent += send(server, message, hmesg_len, 0);
}

char * recv_mesg(int server) {

	short nmesg_len, bytes_recv;
	bytes_recv = 0;
	while ( bytes_recv < sizeof(short))
		bytes_recv += recv(server, &nmesg_len, sizeof(short), 0);
	short hmesg_len = ntohs(nmesg_len);
	char * buffer = malloc(sizeof(char) * (hmesg_len + 1));
	bytes_recv = 0;
	while ( bytes_recv < hmesg_len )
		bytes_recv += recv(server, buffer, hmesg_len, 0);
	buffer[hmesg_len] = '\0';
	return buffer;
}
