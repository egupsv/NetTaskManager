package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;

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
