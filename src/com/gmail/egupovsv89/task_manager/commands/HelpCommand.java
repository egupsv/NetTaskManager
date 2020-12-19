package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.HelpException;
import com.gmail.egupovsv89.task_manager.TaskRepository;

public class HelpCommand implements Command {
    @Override
    public void getDescription() throws HelpException {
    }

    @Override
    public void execute(TaskRepository tr) {
        System.out.println("list of commands:");
        new AddCommand().getDescription();
        new ShowCommand().getDescription();
        new ShowAllCommand().getDescription();
        new ChangeCommand("name").getDescription();
        new ChangeCommand("description").getDescription();
        new ChangeCommand("time").getDescription();
        new RemoveCommand().getDescription();
        new CopyCommand().getDescription();
        new CompleteCommand().getDescription();
        new ClearCommand().getDescription();
        new SaveCommand().getDescription();
        System.out.println("exit - exit");
    }
}
