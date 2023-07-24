import java.io.*;
import java.net.Socket;

class Request implements Runnable {
    private final Socket clientSocket;

    public Request(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter responseWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String requestLine = requestReader.readLine();
            System.out.println("Received request: " + requestLine);

            if (requestLine.startsWith("GET")) {
                String response = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<html><body><h1>Hello, World!</h1></body></html>";

                responseWriter.write(response);
                responseWriter.flush();
            } else {
                String response = "HTTP/1.1 501 Not Implemented\r\n\r\n";
                responseWriter.write(response);
                responseWriter.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}