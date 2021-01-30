package com.gmail.egupovsv89.task_manager.net;

import com.gmail.egupovsv89.task_manager.CommandLineUI;
import com.gmail.egupovsv89.task_manager.HelpException;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.users.User;
import com.gmail.egupovsv89.task_manager.users.Users;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The {@code Server} class creates pull of parallel server sockets which interact with clients
 * using mostly string messages
 */
public class Server {
    /**
     * value for total quantity of clients which can work with server at the same time
     */
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    /**
     * The {@code SingleServer} class creates single server socket for one client
     */
    private static class SingleServer implements Runnable {
        /**
         * client socket
         */
        private static Socket client;
        /**
         * registered users
         */
        private static Users users;
        public SingleServer(Socket client, Users users) {
            SingleServer.client = client;
            SingleServer.users = users;
        }
        /**
         * runs single server
         */
        @Override
        public void run() {
            System.out.println("Connection from " + client);
            try {
                DataInputStream dis = new DataInputStream(client.getInputStream());
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                while (!client.isClosed()) {
                    int success = 0;
                    User user = null;
                    String action = dis.readUTF();
                    /*
                    this part is responsible for authorization or registration
                     */
                    if ("Q".equalsIgnoreCase(action)) {
                        client.close();
                        break;
                    }
                    dos.writeUTF("Input login: ");
                    String login = dis.readUTF();
                    dos.writeUTF("Input password: ");
                    String encPassword = dis.readUTF();
                    if ("L".equalsIgnoreCase(action)) {
                        User requiredUser = users.getUser(login);
                        if (requiredUser != null && requiredUser.getEncPassword().equals(encPassword)) {
                            user = requiredUser;
                        }
                    }
                    if ("S".equalsIgnoreCase(action)) {
                        if (users.getUser(login) == null) {
                            user = new User(login, encPassword);
                            users.addUser(user);
                            File userFile = new File("users/" + login + ".txt");
                            userFile.createNewFile();
                            users.save();
                        }
                    }
                    if (user != null) {
                        success = 1;
                    }
                    dos.writeInt(success);
                    TaskRepository tr = new TaskRepository("users/" + login + ".txt", dis, dos);
                    /*
                    starts the timer which keeps track of tasks terms
                     */
                    Timer timer = new Timer();
                    timer.schedule(tr, 0, 60000);
                    /*
                      this part is responsible for work with tasks
                     */
                    String command = dis.readUTF();
                    while(!"exit".equals(command)) {
                        try {
                            CommandLineUI.COMMANDS.get(command).execute(tr, dis, dos);
                        } catch (NullPointerException e) {
                            dos.writeUTF("error: no such command");
                        } catch (ParseException | HelpException e) {
                            e.printStackTrace();
                        }
                        command = dis.readUTF();
                    }
                    dos.writeUTF("Data has been successfully saved");
                    tr.save();
                    client.close();
                }
                System.out.println("Connection from " + client + " is closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(7777);
        Users users = new Users("users.txt");
        System.out.println("ServerSocket awaiting connections...");
        while(!ss.isClosed()) {
            Socket client = ss.accept();
            pool.execute(new SingleServer(client, users));
        }
        pool.shutdown();
    }
}
