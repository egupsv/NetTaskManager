package com.gmail.egupovsv89.task_manager;

import com.gmail.egupovsv89.task_manager.commands.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;

public class CommandLineUI {
    public static void main(String[] args) throws IndexOutOfBoundsException, ParseException {
        TaskRepository tr = new TaskRepository("tasks.txt");
        //TODO 1. extract into constant commands
        HashMap<String, Command> commands = new HashMap<>();
        //tr.inform();
        commands.put("help", new HelpCommand());
        commands.put("add", new AddCommand());
        commands.put("show all", new ShowAllCommand());
        commands.put("show", new ShowCommand());
        commands.put("remove", new RemoveCommand());
        commands.put("change d", new ChangeCommand("description"));
        commands.put("change t", new ChangeCommand("time"));
        commands.put("rename", new ChangeCommand("name"));
        commands.put("copy", new CopyCommand());
        commands.put("clear", new ClearCommand());
        commands.put("complete", new CompleteCommand());
        commands.put("save", new SaveCommand());

        Scanner in = new Scanner(System.in);
        System.out.print("Input a command (or input 'help' for list of commands): ");
        String command = in.nextLine();
        while (!command.equals("exit")) { //TODO 2 compare constant vs variable not visa versa
            commands.get(command).execute(tr); //todo 3 NPE check? is it possible that commands.get(command) returns null?
            in = new Scanner(System.in);
            System.out.print("\nInput a command (or input 'help' for list of commands): ");
            command = in.nextLine();
        }
        in.close();
    }
}
