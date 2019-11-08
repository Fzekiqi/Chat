package sample.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerWorker extends Thread {

    private final Socket socket;
    private final Server server;
    PrintWriter pw;

    public ServerWorker(Server server, Socket sock) throws IOException {
        this.server = server;
        this.socket = sock;
        this.pw = new PrintWriter(socket.getOutputStream(), true);
    }//Constructor

    @Override
    public void run() {
        try {
            readAndWrite();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//run

    private void readAndWrite() throws IOException {

        while (true) {
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            String message;

            while ((message = input.readLine()) != null) {
                List<ServerWorker> workerList = server.getWorkerList();
                System.out.println(message);

                for (ServerWorker sw : workerList) {
                    sw.send(message);

                }
            }
        }
    }//readAndWrite

    private void send(String message) throws IOException {
        pw.println(message);
    }
}

