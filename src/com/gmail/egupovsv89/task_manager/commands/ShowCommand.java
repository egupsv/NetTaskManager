package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShowCommand implements Command {

    @Override
    public String getDescription() {
        return "show - show any task (tasks);";
    }

    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IOException {
        dos.writeUTF("Input name of task (tasks) you want: ");
        String name = dis.readUTF();
        dos.writeUTF(Utils.show(tr.getTasksByName(name), "no such tasks, press \"Enter\""));
        dis.readUTF();
        dos.writeUTF("OK");
    }
}
