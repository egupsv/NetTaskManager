package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShowAllCommand implements Command {

    @Override
    public String getDescription() {
        return "show all - show full list of tasks;";
    }

    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IOException {
        dos.writeUTF(Utils.show(tr.getTasks(), "no tasks") + "\npress \"Enter\"");
        dis.readUTF();
        dos.writeUTF("OK");
    }
}
