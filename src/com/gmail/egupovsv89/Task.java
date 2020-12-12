package com.gmail.egupovsv89;

import com.gmail.egupovsv89.commands.AddCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Task {
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
    public boolean equals(Object obj) {
        return (this == obj);
        //return super.equals(obj);
    }

}
