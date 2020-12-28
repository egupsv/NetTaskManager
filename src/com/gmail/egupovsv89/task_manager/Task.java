package com.gmail.egupovsv89.task_manager;

import java.io.Serializable;
import java.util.Date;
import java.util.TimerTask;

public class Task extends TimerTask implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private boolean completed = false;
    private final int id;
    private Date time;

    public Task(String name, String description, Date time, int id) {
        this.name = name;
        this.time = time;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Date getTime() {
        return time;
    }

    public int getId() { return id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    @Override
    public void run() {
        final String answer = System.console().readLine("\r\ntime for " + name + " has come" + "\ndo you want to complete it right now? y/n ");
        System.out.println("current answer [" + answer + "]");
        if ("y".equals(answer)) {
            setCompleted(true);
            System.out.println("task has completed");
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", time=" + time +
                '}';
    }
}
