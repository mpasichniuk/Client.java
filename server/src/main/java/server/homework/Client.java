package server.homework;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class Client implements Runnable {
        private Socket socket;
        private PrintWriter printWriter;
        private Scanner in;
        private String name;

        public Client(Socket socket, Server server) {
            try {
                this.socket = socket;
                printWriter = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());
                name = "server.homework.Client";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                if (in.hasNext()) {
                    String w = in.nextLine();
                    System.out.println(name + ": " + w);
                    printWriter.println("entered: " + w);
                    printWriter.flush();
                    if (w.equalsIgnoreCase("END"))
                        break;
                }
            }
            Future<?> awaitingAuth = Executors.newSingleThreadExecutor().submit(() -> {
                while (true) {
                    if (in.hasNext()) {
                        String clientMessage = in.nextLine();
                        return in.hasNext(clientMessage);
                    }
                }
            });

            try {
                String login = (String) awaitingAuth.get(120, TimeUnit.SECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {

                try {
                    System.out.println("server.homework.Client disconnected");
                    socket.close();
                } catch (IOException c) {
                    c.printStackTrace();
                }

            }
        }

    }
