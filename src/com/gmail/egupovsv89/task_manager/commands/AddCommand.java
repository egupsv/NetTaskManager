package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class AddCommand implements Command {
    public void getDescription() {
        System.out.println("add - add new task;");
    }
    public void execute(TaskRepository tr) throws ParseException {
        Scanner in;
        in = new Scanner(System.in);
        System.out.print("Input name of task: ");
        String name = in.nextLine();
        in = new Scanner(System.in);
        System.out.print("Input description of task: ");
        String description = in.nextLine();
        in = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        System.out.print("Input date (DD.MM.YYYY) and time (hh:mm) for example '01.01.2030 11:30') ");
        String timeInString = in.nextLine();
        Date time = null;
        try {
            time = formatter.parse(timeInString);
            tr.addTask(new Task(name, description, time));
        } catch (ParseException e) {
            System.out.println("wrong date/time format");;
        }
    }
}