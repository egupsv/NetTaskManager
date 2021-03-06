package com.gmail.egupovsv89.task_manager.tasks;


import java.io.*;
import java.util.*;

/**
 * The {@code Users} class represents repository of tasks.
 * this repository is stored in separate file for every single user
 */
public class TaskRepository implements Serializable {
    /**
     * list of tasks
     */
    private List<Task> tasks;


    private static final long serialVersionUID = 1L;

    /**
     * path to user's file which contains repository
     */
    private final String path;

    @SuppressWarnings("unchecked")
    public TaskRepository(String path) throws IOException {
        this.path = path;
        BufferedReader br = new BufferedReader(new FileReader(path));
        if (br.readLine() == null) {
            this.tasks = new ArrayList<>();
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
                this.tasks = (List<Task>) ois.readObject();
            } catch (Exception e) {
                System.out.println(e.getMessage()); //TODO we loose stack trace in such way, only mesage is logged, better to print whole S
            }
        }
    }

    /**
     * calculate value of last ID
     * @return value of last ID
     */
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

    /**
     * @return list of tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * adds new task to repository
     * @param task new task
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * @param name name of task
     * @return list of tasks with specific name
     */
    public List<Task> getTasksByName(String name) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().equals(name)) result.add(task);
        }
        return result;
    }

    /**
     * remove task(s) from repository
     * @param requiredTasks list of tasks which must be removed
     */
    public void removeTasks(List<Task> requiredTasks) {
        tasks.removeAll(requiredTasks);
    }

    /**
     * makes any task completed
     * @param task task which must be completed
     */
    public void completeTask(Task task) {
        task.setCompleted(true);
    }

    /**
     * clear task repository
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * saves current repository to disk
     */
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
}
