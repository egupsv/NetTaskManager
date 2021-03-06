package com.gmail.egupovsv89.task_manager;

import com.gmail.egupovsv89.task_manager.commands.*;

import java.util.HashMap;

/**
 * The {@code CommandLineUI} class provides tool for using commands
 */
public class CommandLineUI {
    /**
     * hashmap for whole set of commands
     */
    public static final HashMap<String, Command> COMMANDS = new HashMap<>();
    static {
        COMMANDS.put("help", new HelpCommand());
        COMMANDS.put("add", new AddCommand());
        COMMANDS.put("show all", new ShowAllCommand());
        COMMANDS.put("show", new ShowCommand());
        COMMANDS.put("remove", new RemoveCommand());
        COMMANDS.put("change d", new ChangeCommand("description"));
        COMMANDS.put("change t", new ChangeCommand("time"));
        COMMANDS.put("rename", new ChangeCommand("name"));
        COMMANDS.put("copy", new CopyCommand());
        COMMANDS.put("clear", new ClearCommand());
        COMMANDS.put("complete", new CompleteCommand());
    }
}
