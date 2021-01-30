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
        List<Task> requiredTasks = Utils.getRequiredTasks(tasks, dis, dos, true);
        if (requiredTasks != null) {
            tr.removeTasks(requiredTasks);
        }
        dos.writeUTF("OK");
    }
}
