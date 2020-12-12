package com.gmail.egupovsv89;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TaskRepository {
    private List<Task> tasks;

    public TaskRepository(List<Task> tasks) {
        this.tasks = tasks;
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }


    public List<Task> getTasksByTime(Date time) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTime() == time) result.add(task);
        }
        return result;
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
        int currentNum = 0;
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



}
