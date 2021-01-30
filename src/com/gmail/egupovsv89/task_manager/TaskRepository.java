package com.gmail.egupovsv89.task_manager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The {@code Users} class represents repository of tasks.
 * this repository is stored in separate file for every single user
 */
public class TaskRepository extends TimerTask implements Serializable {
    /**
     * list of tasks
     */
    private List<Task> tasks;

    /**
     * data input stream
     */
    private final DataInputStream dis;

    /**
     * data output stream
     */
    private final DataOutputStream dos;
    private static final long serialVersionUID = 1L;

    /**
     * path to user's file which contains repository
     */
    private final String path;

    /**
     * template for time used when user inputs time
     */
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

    /**
     * checks if time for any task has come and
     * asks to user if it is necessary to complete task
     */
    @Override
    public void run() {
        Date dateNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(TIMEFORMAT);
        for (Task task : tasks) {
            if(formatter.format(task.getTime()).equals(formatter.format(dateNow))) {
                try {
                    dis.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    dos.writeUTF("\r\ntime for '" + task.getName() + "' has come" + "\ndo you want to complete it right now? y/n ");
                    String answer = dis.readUTF();
                    do {
                        if ("y".equalsIgnoreCase(answer)) {
                            task.setCompleted(true);
                            dos.writeUTF("task has completed, press \"Enter\"");
                            dis.readUTF();
                        }
                        else if (!"n".equalsIgnoreCase(answer)) {
                            dos.writeUTF("wrong answer, use only \"y\" or \"n\"" );
                            answer = dis.readUTF();
                        }
                    } while (!"y".equalsIgnoreCase(answer) && !"n".equalsIgnoreCase(answer));
                    dos.writeUTF("OK");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
