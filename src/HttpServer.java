import java.io.*;
import java.net.*;

public class HttpServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080); // Прослушиваем порт 8080
            System.out.println("HTTP Server started on port 8080.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from " + clientSocket.getInetAddress().getHostAddress());

                Thread thread = new Thread(new Request(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}