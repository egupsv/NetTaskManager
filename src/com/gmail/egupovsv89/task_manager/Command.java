package com.gmail.egupovsv89.task_manager;

import java.text.ParseException;

public interface Command {
    void getDescription ();
    void execute() throws ParseException;
}
