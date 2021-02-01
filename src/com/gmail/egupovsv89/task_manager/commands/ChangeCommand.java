package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskChecker;
import com.gmail.egupovsv89.task_manager.tasks.Task;
import com.gmail.egupovsv89.task_manager.tasks.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * The {@code ChangeCommand} class changes any field of task(s)
 */
public class ChangeCommand implements Command {
    /**
     * field which must be changed (name, description or time)
     */
    private final String field;

    /**
     * Initializes a newly created {@code ChangeCommand} object which works with one field
     */
    public ChangeCommand(String field) {
        this.field = field;
    }

    /**
     * provides description of command used in console interface (displayed when user input 'help' command).
     * work with different fields is provided by using different commands for console interface
     * @return  The resulting string which is part of user manual
     */
    @Override
    public String getDescription() {
        String result;
        switch (field) {
            case ("name"):
                result = "rename - rename any task";
                break;
            case ("description"):
                result ="change d - change description of any task;";
                break;
            case ("time"):
                result = "change t - change time of any task;";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + field);
        }
        return result;
    }

    /**
     * runs execution of command
     * @param   tr task repository (user works with)
     * @param   dis data input stream
     * @param   dos data output stream
     */
    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IndexOutOfBoundsException, IOException {
        dos.writeUTF("Input name of task (tasks) you want to change: ");
        String name = dis.readUTF();
        dos.writeUTF("Input new " + field + " of this task: ");
        String newValue = dis.readUTF();
        List<Task> tasks = tr.getTasksByName(name);
        List<Task> requiredTasks = Utils.getRequiredTasks(tasks, dis, dos, true);
        if (requiredTasks != null) {
            for (Task task : requiredTasks) {
                chooseAction(task, newValue);
            }
        }
        dos.writeUTF("OK");
    }

    /**
     * allows to select a field depending on the command
     * @param   task changing task
     * @param   newValue new value of changing field
     */
    private void chooseAction(Task task, String newValue) {
        switch (field) {
            case ("name"):
                task.setName(newValue);
                break;
            case ("description"):
                task.setDescription(newValue);
                break;
            case ("time"):
                SimpleDateFormat formatter = new SimpleDateFormat(TaskChecker.TIMEFORMAT);
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
