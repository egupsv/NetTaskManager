package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.tasks.Task;
import com.gmail.egupovsv89.task_manager.tasks.TaskRepository;
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
        List<Task> requiredTasks = Utils.getRequiredTasks(tasks, dis, dos, false);
        if (requiredTasks != null) {
            Task task = requiredTasks.get(0);
            tr.completeTask(task);
        }
        dos.writeUTF("OK");
    }
}
