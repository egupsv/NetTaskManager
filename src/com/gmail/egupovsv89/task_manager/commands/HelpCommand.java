package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.CommandLineUI;
import com.gmail.egupovsv89.task_manager.HelpException;
import com.gmail.egupovsv89.task_manager.TaskRepository;

public class HelpCommand implements Command {
    @Override
    public void getDescription() throws HelpException {
    }

    //todo iterate over maps value? Pls refer to comment TODO 1. extract into constant commands
    @Override
    public void execute(TaskRepository tr) throws HelpException {
        System.out.println("list of commands:");
            for (Command value : CommandLineUI.COMMANDS.values()) {
                value.getDescription();
            }
            System.out.println("exit - exit");
    }
}
