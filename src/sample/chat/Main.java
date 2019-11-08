package sample.chat;


public class Main {

    public static void main(String[] args) {
        int port = 12444;
        Server server = new Server(port);
        server.start();
    }
}




