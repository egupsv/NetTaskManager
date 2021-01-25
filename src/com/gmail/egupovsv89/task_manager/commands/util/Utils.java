package com.gmail.egupovsv89.task_manager.commands.util;

import com.gmail.egupovsv89.task_manager.Task;

import java.util.List;

/**
 * The {@code Utils} class provides method(s) which can do auxiliary actions helpful for other classes
 */
public class Utils {
    /**
     * provides list of tasks in human-readable way.
     * @param   tasks
     *          list of tasks to show
     * @param   emptyMessage
     *          message for case of empty list
     * @return  The resulting string
     */
    public static String show(List<Task> tasks, String emptyMessage) {
        if (tasks.isEmpty()) {
            return emptyMessage;
        }
        StringBuilder message = new StringBuilder();
        int index = 0;
        for (Task task : tasks) {
            String isCompleted = task.isCompleted() ? "completed" : "not completed";
            index++;
            message.append("\n").append(index).append(") name: ").append(task.getName()).
                    append("\ndescription: ").append(task.getDescription()).append("\ntime: ").append(task.getTime()).
                    append("\n").append(isCompleted).append("\n");
        }
        return message.toString();
    }
}
