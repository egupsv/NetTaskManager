package com.gmail.egupovsv89.task_manager.net;

import com.gmail.egupovsv89.task_manager.CommandLineUI;
import com.gmail.egupovsv89.task_manager.HelpException;
import com.gmail.egupovsv89.task_manager.TaskChecker;
import com.gmail.egupovsv89.task_manager.tasks.TaskRepository;
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
    public static final int PORT = 7777;

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
         * runs execution of command for user
         * @param   success indicator for 'while' loop
         * @param   dis data input stream
         * @param   dos data output stream
         * @param   action action which was chosen by user (sign up - "s" or log in - "l")
         * @return registered (authorized) user's login
         */
        public String LogInOrSignUp(int success, DataInputStream dis, DataOutputStream dos, String action) throws IOException {
            String login = null;
            while (success == 0) {
                User user = null;
                dos.writeUTF("Input login: ");
                login = dis.readUTF();
                System.out.println(client + " login from client: " + login);
                dos.writeUTF("Input password: ");
                String encPassword = dis.readUTF();
                System.out.println(client + " encrypted password from client: " + encPassword);
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
                    dos.writeInt(success);
                } else {
                    dos.writeInt(success);
                    action = dis.readUTF();
                    System.out.println(client + " action: " + action);
                }
            }
            return login;

        }

        /**
         * runs execution of command for tasks
         * @param   dis data input stream
         * @param   dos data output stream
         * @param   tr current task repository
         */
        public void workWithTasks(DataInputStream dis, DataOutputStream dos, TaskRepository tr) throws IOException, InterruptedException {
            String command;
            do {
                command = dis.readUTF();
                System.out.println(client + " command: " + command);
                if ("".equals(command)) {
                    dos.writeUTF("OK");
                } else {
                    try {
                        CommandLineUI.COMMANDS.get(command).execute(tr, dis, dos);
                        //TODO it's better to check whether is command is null, rather then catch NPE.
                        //TODO moreover error can occur inside command, and curent message will be misleading
                    } catch (NullPointerException e) {
                        dos.writeUTF("error: no such command");
                    } catch (ParseException | HelpException e) {
                        e.printStackTrace();
                    }
                }
            } while (!"exit".equals(command));
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
                    String action = dis.readUTF();
                    System.out.println(client + " action: " + action);
                    if ("Q".equalsIgnoreCase(action)) {
                        client.close();
                        break;
                    }
                    String login = LogInOrSignUp(success, dis, dos, action);
                    TaskRepository tr = new TaskRepository("users/" + login + ".txt");
                    TaskChecker tc = new TaskChecker(tr, dis, dos);

                    /*
                    starts the timer which keeps track of tasks terms
                     */
                    Timer timer = new Timer();
                    timer.schedule(tc, 0, 60000); //TODO don't use magic numbers, it's beter to extract them into variables and give them names

                    workWithTasks(dis, dos, tr);
                    dos.writeUTF("Data has been successfully saved");
                    tr.save();
                    client.close();
                }
                System.out.println("Connection from " + client + " is closed");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        Users users = new Users("users.txt");
        System.out.println("ServerSocket awaiting connections...");
        while(!ss.isClosed()) {
            Socket client = ss.accept();
            pool.execute(new SingleServer(client, users));
        }
        pool.shutdown();
    }
}
