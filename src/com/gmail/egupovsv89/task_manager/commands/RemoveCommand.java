package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * The {@code RemoveCommand} class removes any task(s) from task repository
 */
public class RemoveCommand implements Command {

    /**
     * provides description of command used in console interface (displayed when user input 'help' command).
     * @return  The resulting string which is part of user manual
     */
    @Override
    public String getDescription() {
        return "remove - remove any task;";
    }

    /**
     * runs execution of command
     * @param   tr task repository (user works with)
     * @param   dis data input stream
     * @param   dos data output stream
     */
    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IndexOutOfBoundsException, IOException {
        dos.writeUTF("Input name of task (tasks) you want to remove: ");
        String name = dis.readUTF();
        List<Task> tasks = tr.getTasksByName(name);
        /*
         * name is not unique parameter of task so options are possible
         */
        if (tasks.size() == 1) {
            tr.removeTask(name);
            dos.writeUTF("OK");
        } else if (!tasks.isEmpty()) {
            dos.writeUTF("Here more then one task with the same name, chose the number of one you need or " +
                    "input \"0\" if you want to remove all: " + Utils.show(tasks, ""));
            int num;
            /*
             * getting a specific number of task from user.
             * this command can work with all tasks with the same name (when user inputs '0')
             */
            do {
                num = Integer.parseInt(dis.readUTF());
                if (num == 0) {
                    tr.removeTask(name);
                } else if (num > 0 && num <= tasks.size()) {
                    tr.removeTask(name, num - 1);
                } else {
                    dos.writeUTF("wrong number, try again\n");
                }
            } while (num < 0 || num > tasks.size());
        } else {
            dos.writeUTF(Utils.show(tasks, "no such tasks, press \"Enter\""));
            dis.readUTF();
        }
        dos.writeUTF("OK");
    }
}
