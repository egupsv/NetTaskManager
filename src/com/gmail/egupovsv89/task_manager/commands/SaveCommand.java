package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;

import java.io.*;

public class SaveCommand implements Command {
    @Override
    public void getDescription() {
        System.out.println("save - write list of tasks into file");
    }

    @Override
    public void execute(TaskRepository tr) {
        tr.save();
    }

}
