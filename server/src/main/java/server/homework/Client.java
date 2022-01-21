package server.homework;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

    public class Client implements Runnable {
        private Socket socket;
        private PrintWriter printWriter;
        private Scanner in;
        private String name;

        public Client(Socket socket) {
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


            try {
                System.out.println("server.homework.Client disconnected");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
