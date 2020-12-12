package com.gmail.egupovsv89.commands;

import com.gmail.egupovsv89.Command;
import com.gmail.egupovsv89.TaskRepository;
import com.gmail.egupovsv89.commands.util.Utils;

import java.util.Scanner;

public class ShowCommand implements Command {
    private TaskRepository tr;

    public ShowCommand(TaskRepository tr) {
        this.tr = tr;
    }
    @Override
    public void getDescription() {
System.out.println("show - show any task (tasks);");
    }

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input name of task (tasks) you want: ");
        String name = in.nextLine();
        new Utils().show(tr.getTasksByName(name), "no such tasks");
    }
}
