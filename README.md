## Chess online

### Description
This is a project of the course Programming for the Internet at Stockholm University. With very free boundaries I was allowed to create something in Java with a server/client approach, so I decided to create a game of chess on localhost.

### How to start
Besides of the source code in the src/ directory, there is a .jar-file in the root of the repo. The game expects the clients to connect to ports 2000 and 2001. The program is started by running the .jar: `java -jar Game.jar <your-port> <server> <opponent-port>` as such:

#### Client 1
`java -jar Game.jar 2000 localhost 2001`

#### Client 2
`java -jar Game.jar 2001 localhost 2000`

The GUI/UX of the game could of course be improved, but that was not the focus of this course.
