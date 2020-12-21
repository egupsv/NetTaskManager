package com.gmail.egupovsv89.task_manager;

import com.gmail.egupovsv89.task_manager.commands.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;

public class CommandLineUI {
    public static final String TIMEFORMAT = "dd.MM.yyyy HH:mm";
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
        COMMANDS.put("save", new SaveCommand());
    }
    public static void main(String[] args) throws IndexOutOfBoundsException, ParseException, HelpException {
        TaskRepository tr = new TaskRepository("tasks.txt");
        //tr.inform();
        Scanner in = new Scanner(System.in);
        System.out.print("Input a command (or input 'help' for list of commands): ");
        String command = in.nextLine();
        while (!"exit".equals(command)) {
            try {
                COMMANDS.get(command).execute(tr);
            } catch (NullPointerException e) {
                System.out.println("No such command");;
            }
            in = new Scanner(System.in);
            System.out.print("\nInput a command (or input 'help' for list of commands): ");
            command = in.nextLine();
        }
        in.close();
    }
}
