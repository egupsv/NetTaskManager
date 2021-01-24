package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClearCommand implements Command {
    @Override
    public String getDescription() {
        return "clear - clear list of tasks;";
    }

    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IOException {
        tr.clear();
        dos.writeUTF("OK");
    }
}
