package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.Console;
import java.util.List;

public class CompleteCommand implements Command {
    @Override
    public void getDescription() {
        System.out.println("complete - complete any task");
    }

    @Override
    public void execute(TaskRepository tr) throws IndexOutOfBoundsException{
        final Console console = System.console();
        String name = console.readLine("Input name of task you want to complete: ");
        List<Task> tasks = tr.getTasksByName(name);
        if (tasks.isEmpty()) {
            Utils.show(tasks, "no such tasks");
            return;
        }
        Task task;
        if (tasks.size() > 1) {
            System.out.print("Here more then one task with the same name, chose the number of one you need: ");
            Utils.show(tasks, "");
            task = tasks.get(Integer.parseInt(console.readLine()) - 1);
        } else {
            task = tasks.get(0);
        }
        tr.completeTask(task);
    }
}
