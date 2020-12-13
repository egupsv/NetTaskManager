package com.gmail.egupovsv89.task_manager;

import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;
import java.util.TimerTask;

public class Task extends TimerTask implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private boolean completed = false;
    private Date time;

    public Task(String name, Date time) {
        this.name = name;
        this.time = time;
        this.description = "";
    }

    public Task(String name, String description, Date time) {
        this.name = name;
        this.time = time;
        this.description = description;
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
        Scanner in = new Scanner(System.in);
        System.out.println("time for " + name + " has come");
        System.out.println("do you want to complete it right now? y/n");
        if (in.nextLine() == "y") {
            completed = true;
        }
    }
}
