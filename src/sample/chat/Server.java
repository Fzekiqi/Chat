package sample.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends  Thread{

    private final int port;
    ArrayList<ServerWorker>  workersList=new ArrayList<>();


    public Server(int port) {
        this.port=port;
    }

    public List<ServerWorker> getWorkerList(){
        return workersList;
    }

    @Override
    public void run() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                Socket sock = ss.accept();
                ServerWorker serverWorker=new ServerWorker(this,sock);
                workersList.add(serverWorker);
                serverWorker.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
