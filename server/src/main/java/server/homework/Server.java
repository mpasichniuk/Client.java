package server.homework;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private final int PORT = 8000;

    private List<Client> client;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }


    public void serverSocket() {
        client = new CopyOnWriteArrayList<>();
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Connected");
                new Client(socket, this);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AuthService.disconnect();
            }
        }
    }
}
