package com.gmail.egupovsv89.task_manager;

import java.io.*;
import java.util.*;

public class TaskRepository implements Serializable {
    private List<Task> tasks;
    private static final long serialVersionUID = 1L;
    private final String path;
    private final HashMap<Integer, Timer> timers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public TaskRepository(String path) throws IOException {
        this.path = path;
        BufferedReader br = new BufferedReader(new FileReader(path));
        if (br.readLine() == null) {
            this.tasks = new ArrayList<>();
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
                this.tasks = (List<Task>) ois.readObject();
                for (Task task : tasks) {
                    this.timers.put(task.getId(), new Timer());
                }
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
        this.timers.put(task.getId(), new Timer());
        timers.get(task.getId()).schedule(task, task.getTime());
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
                timers.get(task.getId()).cancel();
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
                    timers.get(task.getId()).cancel();
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
        timers.clear();
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

    public void inform() throws NullPointerException {
        for (Task task : tasks) {
            timers.get(task.getId()).schedule(task, task.getTime());
        }
    }
}
