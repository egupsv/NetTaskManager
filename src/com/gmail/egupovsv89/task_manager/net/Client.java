package com.gmail.egupovsv89.task_manager.net;

import com.gmail.egupovsv89.task_manager.users.Encryptor;

import javax.crypto.NoSuchPaddingException;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class Client {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException {
        final Console console = System.console();
        Socket socket = new Socket("localhost", 7777);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        String login = null;
        int success = 0;
        while (success == 0) {
            String action = console.readLine("Input 'L' to log in\nInput 'S' to sign up\nInput 'Q' to quit\n");
            dos.writeUTF(action);
            if ("Q".equalsIgnoreCase(action)) {
                socket.close();
                return;
            }
            login = console.readLine(dis.readUTF());
            dos.writeUTF(login);
            String password = console.readLine(dis.readUTF());
            String encPassword = new Encryptor().encrypt(password);
            dos.writeUTF(encPassword);
            success = dis.readInt();
            if (success == 0 && ("L".equalsIgnoreCase(action))) {
                System.out.println("login or password is not correct");
            }
            if (success == 0 && ("S".equalsIgnoreCase(action))) {
                System.out.println("such login is already taken");
            }

        }
        System.out.println("Hello " + login);
        success = 0;
        String command = console.readLine("\nInput a command (or input 'help' for list of commands): ");
        while (!"exit".equals(command)) {
            dos.writeUTF(command);
            while (success == 0) {
                String incomingMessage = dis.readUTF();
                if ("OK".equals(incomingMessage)) {
                    success = 1;
                } else if (incomingMessage.contains("error")) {
                    success = 1;
                    System.out.println(incomingMessage);
                } else {
                    String outgoingMessage = console.readLine(incomingMessage);
                    dos.writeUTF(outgoingMessage);
                }
            }
            success = 0;
            command = console.readLine("\nInput a command (or input 'help' for list of commands): ");
        }
        dos.writeUTF(command);
        System.out.println(dis.readUTF());
        socket.close();
    }
}
