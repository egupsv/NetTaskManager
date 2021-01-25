package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.CommandLineUI;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The {@code CopyCommand} class copies any task of task repository to same task repository changing task time
 */
public class CopyCommand implements Command {

    /**
     * provides description of command used in console interface (displayed when user input 'help' command).
     * @return  The resulting string which is part of user manual
     */
    @Override
    public String getDescription() {
        return "copy - copy task (new task will be uncompleted)";
    }

    /**
     * runs execution of command
     * @param   tr task repository (user works with)
     * @param   dis data input stream
     * @param   dos data output stream
     */
    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IndexOutOfBoundsException, IOException {
        dos.writeUTF("Input name of task you want to copy: ");
        String name = dis.readUTF();
        dos.writeUTF("Input time for new task: ");
        String timeInString = dis.readUTF();
        Date time;
        try {
            time = new SimpleDateFormat(CommandLineUI.TIMEFORMAT).parse(timeInString);
            if (tr.getTasksByName(name).isEmpty()) {
                dos.writeUTF(Utils.show(tr.getTasksByName(name), "no such tasks, press \"Enter\""));
                dis.readUTF();
                dos.writeUTF("OK");
                return;
            }
            Task task = null;
            List<Task> tasks = tr.getTasksByName(name);
            /*
             * name is not unique parameter of task so options are possible
             */
            if (tasks.size() > 1) {
                dos.writeUTF("Here more then one task with the same name, chose the number of one you need: " + Utils.show(tasks, ""));
                int num;
                /*
                 * getting a specific number of task from user.
                 */
                do {
                    num = Integer.parseInt(dis.readUTF());
                    if (num > 0 && num <= tasks.size()) {
                        task = tasks.get(num - 1);
                    } else {
                        dos.writeUTF("wrong number, try again\n");
                    }
                } while (num <= 0 || num > tasks.size());
            } else {
                task = tasks.get(0);
            }
            tr.addTask(new Task(task.getName(), task.getDescription(), time, tr.calculateMaxId() + 1));
            dos.writeUTF("OK");
        } catch (ParseException e) {
            dos.writeUTF("wrong date/time format, press \"Enter\"");
            dis.readUTF();
            dos.writeUTF("OK");
        }

    }
}
