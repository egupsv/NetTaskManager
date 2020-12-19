package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;

public class ClearCommand implements Command {
    @Override
    public void getDescription() {
        System.out.println("clear - clear list of tasks;");
    }

    @Override
    public void execute(TaskRepository tr) {
        tr.clear();
    }
}
