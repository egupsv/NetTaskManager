package com.gmail.egupovsv89.task_manager.users;

import java.io.*;
import java.util.*;

public class Users implements Serializable {
    private List<User> users;
    private static final long serialVersionUID = 1L;
    private final String path;

    @SuppressWarnings("unchecked")
    public Users(String path) throws IOException {
        this.path = path;
        BufferedReader br = new BufferedReader(new FileReader(path));
        if (br.readLine() == null) {
            this.users = new ArrayList<>();
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {

                this.users = (List<User>) ois.readObject();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void addUser(User user) {
        this.users.add(user);
    }
    public User getUser(String login) {
        for (User user : users) {
            if (user.getName().equals(login)) {
                return user;
            }
        }
        return null;
    }
    public void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
        {
            oos.writeObject(users);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
