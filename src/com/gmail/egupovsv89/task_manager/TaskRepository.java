package com.gmail.egupovsv89.task_manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Timer;

public class TaskRepository implements Serializable {
    private List<Task> tasks;
    private static final long serialVersionUID = 1L;

    public TaskRepository(List<Task> tasks) {
        this.tasks = tasks;
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public List<Task> getTasksByName(String name) {
        List<Task> result = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.getName().equals(name)) result.add(task);
        }
        return result;
    }

    public void removeTask(String name) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (name.equals(task.getName())) tasks.remove(task);
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

    public void inform() {
        for (Task task : tasks) {
            Timer timer = new Timer();
            timer.schedule(task, task.getTime());
        }

    }

}
