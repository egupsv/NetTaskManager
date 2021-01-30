package com.gmail.egupovsv89.task_manager.commands.util;

import com.gmail.egupovsv89.task_manager.Task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * provides list of required tasks if names match when command is used.
     * @param   tasks
     *          list of tasks with the same name
     * @param   dis
     *          data input stream
     * @param   dos
     *          data output stream
     * @param   isZeroUsed
     *          true if command may be applied to the whole list, otherwise false
     * @return  The resulting list
     */
    public static List<Task> getRequiredTasks(List<Task> tasks, DataInputStream dis, DataOutputStream dos, boolean isZeroUsed) throws IOException {
        if (tasks.isEmpty()) {
            dos.writeUTF(Utils.show(tasks, "no such tasks, press \"Enter\""));
            dis.readUTF();
            return null;
        }
        if (tasks.size() == 1) {
            return tasks;
        }
        List<Task> result = new ArrayList<>();
        dos.writeUTF("Here more then one task with the same name, chose the number of one you need or " +
                "input \"0\" if you want to remove all: " + Utils.show(tasks, ""));
        int num;
        int lowerValue = isZeroUsed ? 0 : 1;
        /*
         * getting a specific number of task from user.
         */
        do {
            num = Integer.parseInt(dis.readUTF());
            if (num == 0 && isZeroUsed) {
                result = tasks;
            } else if (num > 0 && num <= tasks.size()) {
                result.add(tasks.get(num - 1));
            } else {
                dos.writeUTF("wrong number, try again\n");
            }
        } while (num < lowerValue || num > tasks.size());
        return result;
    }
}
