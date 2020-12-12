package com.gmail.egupovsv89.commands;

import com.gmail.egupovsv89.Command;
import com.gmail.egupovsv89.TaskRepository;
import com.gmail.egupovsv89.commands.util.Utils;

public class ShowAllCommand implements Command {
    private TaskRepository tr;

    public ShowAllCommand(TaskRepository tr) {
        this.tr = tr;
    }
    @Override
    public void getDescription() {
        System.out.println("show all - show full list of tasks;");
    }

    @Override
    public void execute() {
        new Utils().show(tr.getTasks(), "no tasks");
    }
}
