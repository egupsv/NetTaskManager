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
 * The {@code CompleteCommand} class completes any task
 */
public class CompleteCommand implements Command {
    /**
     * provides description of command used in console interface (displayed when user input 'help' command).
     * @return  The resulting string which is part of user manual
     */
    @Override
    public String getDescription() {
        return "complete - complete any task";
    }

    /**
     * runs execution of command
     * @param   tr task repository (user works with)
     * @param   dis data input stream
     * @param   dos data output stream
     */
    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IndexOutOfBoundsException, IOException {
        dos.writeUTF("Input name of task you want to complete: ");
        String name = dis.readUTF();
        List<Task> tasks = tr.getTasksByName(name);
        /*
         * name is not unique parameter of task so options are possible
         */
        if (tasks.isEmpty()) {
            dos.writeUTF(Utils.show(tasks, "no such tasks, press \"Enter\""));
            dis.readUTF();
            dos.writeUTF("OK");
            return;
        }
        Task task = null;
        int num;
        if (tasks.size() > 1) {
            dos.writeUTF("Here more then one task with the same name, chose the number of one you need: " + Utils.show(tasks, ""));
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
        tr.completeTask(task);
        dos.writeUTF("OK");
    }
}
