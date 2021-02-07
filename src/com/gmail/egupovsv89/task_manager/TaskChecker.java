package com.gmail.egupovsv89.task_manager;

import com.gmail.egupovsv89.task_manager.tasks.Task;
import com.gmail.egupovsv89.task_manager.tasks.TaskRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class TaskChecker extends TimerTask implements Serializable {
    private final TaskRepository tr;
    /**
     * data input stream
     */
    private final DataInputStream dis; //TODO unused?

    /**
     * data output stream
     */
    private final DataOutputStream dos;
    /**
     * template for time used when user inputs time
     */
    public static final String TIMEFORMAT = "dd.MM.yyyy HH:mm";

    public TaskChecker(TaskRepository tr, DataInputStream dis, DataOutputStream dos) {
        this.tr = tr;
        this.dis = dis;
        this.dos = dos;
    }

    /**
     * checks if time for any task has come and notify user
     */
    @Override
    public void run() {
        Date dateNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(TIMEFORMAT);
        List<Task> tasks = tr.getTasks();
        for (Task task : tasks) {
            //TODO too long expession, please split it into several ones
            if (formatter.format(task.getTime()).equals(formatter.format(dateNow)) && !task.isCompleted()) {
                try {
                    dos.writeUTF("\r\n!!!!!!!!!!time for '" + task.getName() + "' has come!!!!!!!!!!\n don't forget to complete");
                } catch (IOException e) {// | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
