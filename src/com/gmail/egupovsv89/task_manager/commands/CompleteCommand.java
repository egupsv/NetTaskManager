package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.Task;
import com.gmail.egupovsv89.task_manager.TaskRepository;
import com.gmail.egupovsv89.task_manager.commands.util.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class CompleteCommand implements Command {
    @Override
    public String getDescription() {
        return "complete - complete any task";
    }

    @Override
    public void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws IndexOutOfBoundsException, IOException {
        dos.writeUTF("Input name of task you want to complete: ");
        String name = dis.readUTF();
        List<Task> tasks = tr.getTasksByName(name);
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
