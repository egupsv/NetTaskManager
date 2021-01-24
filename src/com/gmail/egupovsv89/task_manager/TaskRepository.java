package com.gmail.egupovsv89.task_manager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskRepository extends TimerTask implements Serializable {
    private List<Task> tasks;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private static final long serialVersionUID = 1L;
    private final String path;
    public static final String TIMEFORMAT = "dd.MM.yyyy HH:mm";

    @SuppressWarnings("unchecked")
    public TaskRepository(String path, DataInputStream dis, DataOutputStream dos) throws IOException {
        this.path = path;
        this.dis = dis;
        this.dos = dos;
        BufferedReader br = new BufferedReader(new FileReader(path));
        if (br.readLine() == null) {
            this.tasks = new ArrayList<>();
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
                this.tasks = (List<Task>) ois.readObject();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int calculateMaxId() {
        int maxId = 0;
        for (Task task : tasks) {
            int id = task.getId();
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public List<Task> getTasksByName(String name) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().equals(name)) result.add(task);
        }
        return result;
    }

    public void removeTask(String name) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (name.equals(task.getName())) {
                tasks.remove(task);
            }
        }
    }
    public void removeTask(String name, int num) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (name.equals(task.getName())) {
                i++;
                if (i == num) {
                    tasks.remove(task);
                }

            }
        }
    }

    public void completeTask(Task task) {
        task.setCompleted(true);
    }

    public void clear() {
        tasks.clear();
    }

    public void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
        {
            oos.writeObject(tasks);
            System.out.println("Data has been successfully saved to disk");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        Date dateNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(TIMEFORMAT);
        for (Task task : tasks) {
            if(formatter.format(task.getTime()).equals(formatter.format(dateNow))) {
                try {
                    dos.writeUTF("\r\ntime for " + task.getName() + " has come" + "\ndo you want to complete it right now? y/n ");
                    String answer = dis.readUTF();
                    if ("y".equals(answer)) {
                        task.setCompleted(true);
                        dos.writeUTF("task has completed, press any key");
                        dis.readUTF();
                        dos.writeUTF("OK");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
