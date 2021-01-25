package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@code ShowCommand} class displays task(s) with a specific name in current task repository
 */
public class ShowCommand implements Command {

    /**
     * provides description of command used in console interface (displayed when user input 'help' command).
     * @return  The resulting string which is part of user manual
     */
    @Override
    public String getDescription() {
        return "show - show any task (tasks);";
    }

    /**
     * runs execution of command
     * @param   tr task repository (user works with)
     * @param   dis data input stream
     * @param   dos data output stream
     */
    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IOException {
        dos.writeUTF("Input name of task (tasks) you want: ");
        String name = dis.readUTF();
        dos.writeUTF(Utils.show(tr.getTasksByName(name), "no such tasks, press \"Enter\""));
        dis.readUTF();
        dos.writeUTF("OK");
    }
}
