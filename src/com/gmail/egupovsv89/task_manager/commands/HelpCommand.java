package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.CommandLineUI;
import com.gmail.egupovsv89.task_manager.HelpException;
import com.gmail.egupovsv89.task_manager.tasks.TaskRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@code HelpCommand} class displays list of commands available to user
 */
public class HelpCommand implements Command {
    /**
     * @throws  HelpException
     *          because HelpCommand doesn't have description and doesn't appear in user manual
     */
    @Override
    public String getDescription() throws HelpException {
        return null;
    }

    /**
     * runs execution of command (in case of HelpCommand method provides description of
     * all commands used in console interface)
     * @param   tr task repository (user works with)
     * @param   dis data input stream
     * @param   dos data output stream
     */
    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws HelpException, IOException {
        StringBuilder message = new StringBuilder("list of commands:");
            for (Command value : CommandLineUI.COMMANDS.values()) {
                if (value.getDescription() != null) {
                    message.append("\n").append(value.getDescription());
                }
            }
            dos.writeUTF(message.toString() + "\npress \"Enter\"");
            dis.readUTF();
            dos.writeUTF("OK");
    }
}
