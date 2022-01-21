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

            try {
            try {
                serverSocket = new ServerSocket(8000);
                authService = new BaseAuthService();
                authService.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Waiting...");
            client = new ArrayList<>();

            while (true) {
                try {
                    socket = serverSocket.accept();
                    System.out.println("Connected");
                    new Thread(new Client(socket)).start();
                } catch (IOException e) {
                        System.out.println("Ошибка в работе сервера");
                    } finally {
                        if (authService != null) {
                            authService.stop();
                        }
                    }
                }

        } finally {
            try {
                socket.close();
                serverSocket.close();
                System.out.println("closed");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        }

    }



