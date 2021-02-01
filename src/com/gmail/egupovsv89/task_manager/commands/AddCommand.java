package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.tasks.Task;
import com.gmail.egupovsv89.task_manager.tasks.TaskRepository;
import com.gmail.egupovsv89.task_manager.TaskChecker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@code AddCommand} class adds new task to task repository
 */
public class AddCommand implements Command {

    /**
     * provides description of command used in console interface (displayed when user input 'help' command).
     * @return  The resulting string which is part of user manual
     */
    public String getDescription() {
        return "add - add new task;";
    }

    /**
     * runs execution of command
     * @param   tr task repository (user works with)
     * @param   dis data input stream
     * @param   dos data output stream
     */
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IOException {
        dos.writeUTF("Input name of task: ");
        String name = dis.readUTF();
        dos.writeUTF("Input description of task: ");
        String description = dis.readUTF();
        SimpleDateFormat formatter = new SimpleDateFormat(TaskChecker.TIMEFORMAT);
        dos.writeUTF("Input date (DD.MM.YYYY) and time (hh:mm) for example '01.01.2030 11:30'): ");
        String timeInString = dis.readUTF();
        Date time;
        try {
            time = formatter.parse(timeInString);
            tr.addTask(new Task(name, description, time, tr.calculateMaxId() + 1));
        } catch (ParseException e) {
            dos.writeUTF("wrong date/time format, press any key");
            dis.readUTF();
        }
        dos.writeUTF("OK");
    }
}