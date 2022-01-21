package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TextField textField;
    public TextArea jta;
    private final String ADDRESS = "localhost";
    private final int PORT = 8000;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    @FXML
    public void ButtonClickSendText(ActionEvent a) {
        if (textField.getText().length()>0){
            try {
                dataOutputStream.writeUTF(textField.getText());
                textField.clear();
                textField.requestFocus();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            socket = new Socket(ADDRESS, PORT);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true){
                        String s = dataInputStream.readUTF();

                        if (s.equals("/end")) {
                            break;
                        }
                        jta.appendText(s + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }catch (IOException e){
            e.printStackTrace();
        }

        }
    }