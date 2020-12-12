package com.gmail.egupovsv89.commands;

import com.gmail.egupovsv89.Command;
import com.gmail.egupovsv89.Task;
import com.gmail.egupovsv89.TaskRepository;

import java.util.ArrayList;
import java.text.ParseException;

public class ClearCommand implements Command {
    private TaskRepository tr;

    public ClearCommand(TaskRepository tr) {
        this.tr = tr;
    }
    @Override
    public void getDescription() {
        System.out.println("clear - clear list of tasks;");
    }

    @Override
    public void execute() {
        tr.clear();
    }
}
