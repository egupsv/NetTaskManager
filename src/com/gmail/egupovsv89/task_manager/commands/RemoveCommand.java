package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.util.List;
import java.util.Scanner;

public class RemoveCommand implements Command {

    @Override
    public void getDescription() {
        System.out.println("remove - remove any task;");
    }

    @Override
    public void execute(TaskRepository tr) throws IndexOutOfBoundsException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input name of task (tasks) you want to remove: ");
        String name = in.nextLine();
        List<Task> tasks = tr.getTasksByName(name);
        if (tasks.size() == 1) {
            tr.removeTask(name);
        } else if (!tasks.isEmpty()) {
            in = new Scanner(System.in);
            System.out.print("Here more then one task with the same name, chose the number of one you need or input \"0\" if you want to remove all: ");
            Utils.show(tasks, "");
            int num = in.nextInt();
            if (num == 0) {
                tr.removeTask(name);
            } else {
                tr.removeTask(name, num);
            }
        } else {
            Utils.show(tasks, "no such tasks");
        }
    }
}
