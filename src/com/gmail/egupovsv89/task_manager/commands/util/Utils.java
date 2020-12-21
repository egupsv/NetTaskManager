package com.gmail.egupovsv89.task_manager.commands.util;

import com.gmail.egupovsv89.task_manager.Task;

import java.util.List;

public class Utils {
    public static void show(List<Task> tasks, String emptyMessage) {
        if (tasks.isEmpty()) {
            System.out.print(emptyMessage);
        }
        int index = 0;
        for (Task task : tasks) {
            String isCompleted = task.isCompleted() ? "completed" : "not completed";
            index++;
            System.out.print("\n" + index + ") name: " + task.getName() + "\ndescription: " + task.getDescription() +
                    "\ntime: " + task.getTime() + "\n" + isCompleted + "\n");
        }
    }
}
