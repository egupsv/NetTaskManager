package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.CommandLineUI;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CopyCommand implements Command {

    @Override
    public void getDescription() {
        System.out.println("copy - copy task (new task will be uncompleted)");
    }

    @Override
    public void execute(TaskRepository tr) throws IndexOutOfBoundsException{
        Scanner in = new Scanner(System.in);
        System.out.print("Input name of task you want to copy: ");
        String name = in.nextLine();
        in = new Scanner(System.in);
        System.out.print("Input time for new task: ");
        String timeInString = in.nextLine();
        Date time;
        try {
            time = new SimpleDateFormat(CommandLineUI.TIMEFORMAT).parse(timeInString);
            if (tr.getTasksByName(name).isEmpty()) {
                Utils.show(tr.getTasksByName(name), "no such tasks");
                in.close();
                return;
            }
            Task task;
            List<Task> tasks = tr.getTasksByName(name);
            if (tasks.size() > 1) {
                in = new Scanner(System.in);
                System.out.print("Here more then one task with the same name, chose the number of one you need: ");
                Utils.show(tasks, "");
                task = tasks.get(in.nextInt() - 1);
            } else {
                task = tasks.get(0);
            }
            tr.addTask(new Task(task.getName(), task.getDescription(), time));
        } catch (ParseException e) {
            System.out.println("wrong date/time format");
        }
    }
}
