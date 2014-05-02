OBJS = 
CC = gcc
DEBUG = -g
CFLAGS = -Wall -c
LFLAGS = -Wall

all: server client


server : $(OBJS)
	$(CC) $(LFLAGS) $(OBJS) server.c -o mtftpd

client : $(OBJS)
	$(CC) $(LFLAGS) $(OBJS) client.c -o mtftp

clean:
	\rm mtftpd mtftp
