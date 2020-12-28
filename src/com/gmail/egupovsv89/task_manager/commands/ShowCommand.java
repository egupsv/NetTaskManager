package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.Console;

public class ShowCommand implements Command {

    @Override
    public void getDescription() {
System.out.println("show - show any task (tasks);");
    }

    @Override
    public void execute(TaskRepository tr) {
        final Console console = System.console();
        String name = console.readLine("Input name of task (tasks) you want: ");
        Utils.show(tr.getTasksByName(name), "no such tasks");
    }
}
