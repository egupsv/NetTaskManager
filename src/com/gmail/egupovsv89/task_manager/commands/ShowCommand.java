package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.util.Scanner;

public class ShowCommand implements Command {

    @Override
    public void getDescription() {
System.out.println("show - show any task (tasks);");
    }

    @Override
    public void execute(TaskRepository tr) {
        Scanner in = new Scanner(System.in);
        System.out.print("Input name of task (tasks) you want: ");
        String name = in.nextLine();
        new Utils().show(tr.getTasksByName(name), "no such tasks");
    }
}
