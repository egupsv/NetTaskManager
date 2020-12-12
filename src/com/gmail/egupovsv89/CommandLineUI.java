package com.gmail.egupovsv89;

import com.gmail.egupovsv89.commands.*;
import com.gmail.egupovsv89.commands.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CommandLineUI {
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

    public static void main(String[] args) throws ParseException, IndexOutOfBoundsException {
        TaskRepository tr = new TaskRepository(new ArrayList<Task>());
        Scanner in = new Scanner(System.in);
        System.out.print("Input a command (or input 'help' for list of commands): ");
        String command = in.nextLine();
        while (!command.equals("exit")) {
            switch (command) {
                case ("help"):
                    new HelpCommand(tr).execute();
                    break;
                case ("add"):
                    new AddCommand(tr).execute();
                    break;
                case ("show all"):
                    new ShowAllCommand(tr).execute();
                    break;
                case ("show"):
                    new ShowCommand(tr).execute();
                    break;
                case ("remove"):
                    new RemoveCommand(tr).execute();
                    break;
                case ("rename"):
                    new ChangeCommand(tr, "name").execute();
                    break;
                case ("change d"):
                    new ChangeCommand(tr, "description").execute();
                    break;
                case ("change t"):
                    new ChangeCommand(tr, "time").execute();
                    break;
                case ("copy"):
                    new CopyCommand(tr).execute();
                    break;
                case ("clear"):
                    new ClearCommand(tr).execute();
                    break;
            }
            in = new Scanner(System.in);
            System.out.print("\nInput a command (or input 'help' for list of commands): ");
            command = in.nextLine();
        }
        in.close();
    }


}
