package com.gmail.egupovsv89.task_manager.commands;

import com.gmail.egupovsv89.task_manager.Command;
import com.gmail.egupovsv89.task_manager.TaskRepository;

import java.io.*;

public class SaveCommand implements Command {
    private TaskRepository tr;

    public SaveCommand(TaskRepository tr) {
        this.tr = tr;
    }
    @Override
    public void getDescription() {
        System.out.println("save - write list of tasks into file");
    }

    @Override
    public void execute() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tasks.txt")))
        {
            oos.writeObject(tr);
            System.out.println("Data has been successfully saved to disk");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void serializeTR (TaskRepository tr, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        ObjectOutputStream oos = new ObjectOutputStream(dos);
        oos.writeObject(tr);
    }
}
