package sample.chat;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application implements Runnable {

    String hostName = "localhost";
    int portNumber = 12444;
    Socket socket;
    Stage window;
    Scene scene;
    TextArea monitor = new TextArea();
    TextField answerField = new TextField();
    BorderPane layout;
    Thread thread = new Thread(this);


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Riddler Client");
        socket = new Socket(hostName, portNumber);
        window = stage;
        monitor.setEditable(false);
        monitor.setWrapText(true);
        answerField.setOnAction(eventHandler);
        layout = new BorderPane();
        layout.setCenter(monitor);
        layout.setBottom(answerField);
        scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.show();
        thread.start();

    }//start

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                monitor.appendText(message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//run

    EventHandler<ActionEvent> eventHandler = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String inputFromUser = answerField.getText();
                out.println("Fazli: " + inputFromUser);
                answerField.clear();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    };//eventHandler
}
