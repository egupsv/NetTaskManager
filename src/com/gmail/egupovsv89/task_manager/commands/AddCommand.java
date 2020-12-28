package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.CommandLineUI;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCommand implements Command {
    public void getDescription() {
        System.out.println("add - add new task;");
    }
    public void execute(TaskRepository tr) {
        final Console console = System.console();
        String name = console.readLine("Input name of task: ");
        String description = console.readLine("Input description of task: ");
        SimpleDateFormat formatter = new SimpleDateFormat(CommandLineUI.TIMEFORMAT);
        String timeInString = console.readLine("Input date (DD.MM.YYYY) and time (hh:mm) for example '01.01.2030 11:30'): ");
        Date time;
        try {
            time = formatter.parse(timeInString);
            tr.addTask(new Task(name, description, time, tr.calculateMaxId() + 1));
        } catch (ParseException e) {
            System.out.println("wrong date/time format");
        }
    }
}