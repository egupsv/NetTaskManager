package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.CommandLineUI;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class ChangeCommand implements Command {
    private final String field;
    public ChangeCommand(String field) {
        this.field = field;
    }

    @Override
    public void getDescription() {
        switch (field) {
            case ("name"):
                System.out.println("rename - rename any task");
                break;
            case ("description"):
                System.out.println("change d - change description of any task;");
                break;
            case ("time"):
                System.out.println("change t - change time of any task;");
                break;
        }
    }

    @Override
    public void execute(TaskRepository tr) throws IndexOutOfBoundsException {
        final Console console = System.console();
        String name = console.readLine("Input name of task (tasks) you want to change: ");
        String newValue = console.readLine("Input new " + field + " of this task: ");
        List<Task> tasks = tr.getTasksByName(name);
        if (tasks.size() == 1) {
            Task task = tasks.get(0);
            chooseAction(task, newValue);
        } else if (!tasks.isEmpty()) {
            System.out.print("Here more then one task with the same name, chose the number of one you need or input \"0\" if you want to change all: ");
            Utils.show(tasks, "");
            int num = Integer.parseInt(console.readLine());
            if (num == 0) {
                for (Task task : tasks) {
                    chooseAction(task, newValue);
                }
            } else {
                Task task = tasks.get(num - 1);
                chooseAction(task, newValue);
            }
        } else {
            Utils.show(tasks, "no such tasks");
        }
    }

    private void chooseAction(Task task, String newValue) {
        switch (field) {
            case ("name"):
                task.setName(newValue);
                break;
            case ("description"):
                task.setDescription(newValue);
                break;
            case ("time"):
                SimpleDateFormat formatter = new SimpleDateFormat(CommandLineUI.TIMEFORMAT);
                try {
                    Date time = formatter.parse(newValue);
                    task.setTime(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
