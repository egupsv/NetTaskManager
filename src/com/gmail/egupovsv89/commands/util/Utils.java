package com.gmail.egupovsv89.commands.util;

import com.gmail.egupovsv89.Task;

import java.util.List;

public class Utils {
    public static void show(List<Task> tasks, String emptyMessage) {
        if (tasks.isEmpty()) {
            System.out.print(emptyMessage);
        }
        int i = 0;
        for (Task task : tasks) {
            String isCompleted = task.isCompleted() ? "completed" : "not completed";
            i++;
            System.out.print("\n" + i + ") name: " + task.getName() + "\ndescription: " + task.getDescription() +
                    "\ntime: " + task.getTime() + "\n" + isCompleted + "\n");
        }
    }
}
