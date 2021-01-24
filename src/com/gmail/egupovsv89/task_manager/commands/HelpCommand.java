package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.CommandLineUI;
import com.gmail.egupovsv89.task_manager.HelpException;
import com.gmail.egupovsv89.task_manager.TaskRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HelpCommand implements Command {
    @Override
    public String getDescription() throws HelpException {
        return null;
    }

    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws HelpException, IOException {
        StringBuilder message = new StringBuilder("list of commands:");
            for (Command value : CommandLineUI.COMMANDS.values()) {
                message.append("\n").append(value.getDescription());
            }
            dos.writeUTF(message.toString() + "\npress \"Enter\"");
            dis.readUTF();
            dos.writeUTF("OK");
    }
}
