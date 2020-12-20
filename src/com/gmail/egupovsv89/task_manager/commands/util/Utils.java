package com.gmail.egupovsv89.task_manager.commands.util;

import com.gmail.egupovsv89.task_manager.Task;

import java.util.List;

public class Utils {
    public static void show(List<Task> tasks, String emptyMessage) {
        if (tasks.isEmpty()) {
            System.out.print(emptyMessage);
        }
        int i = 0; //todo it's better to name variables with no meaningfull name (i.e. counter/idx)
        for (Task task : tasks) {
            String isCompleted = task.isCompleted() ? "completed" : "not completed";
            i++;
            System.out.print("\n" + i + ") name: " + task.getName() + "\ndescription: " + task.getDescription() +
                    "\ntime: " + task.getTime() + "\n" + isCompleted + "\n");
        }
    }
}
