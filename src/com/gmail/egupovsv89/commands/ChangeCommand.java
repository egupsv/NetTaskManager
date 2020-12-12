package com.gmail.egupovsv89.commands;

import com.gmail.egupovsv89.Command;
import com.gmail.egupovsv89.Task;
import com.gmail.egupovsv89.TaskRepository;
import com.gmail.egupovsv89.commands.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ChangeCommand implements Command {
    private TaskRepository tr;
    private String field;

    public ChangeCommand(TaskRepository tr, String field) {
        this.tr = tr;
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
    public void execute() throws IndexOutOfBoundsException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input name of task (tasks) you want to change: ");
        String name = in.nextLine();
        in = new Scanner(System.in);
        System.out.print("Input new " + field + " of this task: ");
        String newValue = in.nextLine();
        if (tr.getTasksByName(name).size() == 1) {
            Task task = tr.getTasksByName(name).get(0);
            chooseAction(task, newValue);
        } else if (!tr.getTasksByName(name).isEmpty()) {
            in = new Scanner(System.in);
            System.out.print("Here more then one task with the same name, chose the number of one you need or input \"0\" if you want to change all: ");
            Utils.show(tr.getTasksByName(name), "");
            int num = in.nextInt();
            if (num == 0) {
                for (Task task : tr.getTasksByName(name)) {
                    chooseAction(task, newValue);
                }
            } else {
                Task task = tr.getTasksByName(name).get(num - 1);
                chooseAction(task, newValue);
            }
        } else {
            Utils.show(tr.getTasksByName(name), "no such tasks");
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
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
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
