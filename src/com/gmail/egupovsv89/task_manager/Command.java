package com.gmail.egupovsv89.task_manager;

import com.gmail.egupovsv89.task_manager.tasks.TaskRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
/**
 * The {@code Command} interface is
 * universal template for any command
 */
public interface Command {
    /**
     returns description of command for user's manual
     */
    String getDescription () throws HelpException;
    void execute(TaskRepository tr, DataInputStream dis, DataOutputStream dos) throws ParseException, HelpException, IOException;
}
