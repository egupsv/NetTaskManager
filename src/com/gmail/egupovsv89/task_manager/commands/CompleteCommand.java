package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.util.Scanner;

public class CompleteCommand implements Command {
    @Override
    public void getDescription() {
        System.out.println("complete - complete any task");
    }

    @Override
    public void execute(TaskRepository tr) throws IndexOutOfBoundsException{
        Scanner in = new Scanner(System.in);
        System.out.print("Input name of task you want to complete: ");
        String name = in.nextLine();
        if (tr.getTasksByName(name).isEmpty()) {
            Utils.show(tr.getTasksByName(name), "no such tasks");
            return;
        }
        //TODO 111 the same piece of code as in Copy command.
        Task task;
        if (tr.getTasksByName(name).size() > 1) {
            in = new Scanner(System.in);
            System.out.print("Here more then one task with the same name, chose the number of one you need: ");
            Utils.show(tr.getTasksByName(name), "");
            task = tr.getTasksByName(name).get(in.nextInt() - 1);
        } else {
            task = tr.getTasksByName(name).get(0);
        }
        tr.completeTask(task);
    }
}
