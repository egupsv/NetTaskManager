package com.gmail.egupovsv89.task_manager;

import com.gmail.egupovsv89.task_manager.commands.*;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandLineUI {
    public static void main(String[] args) throws IndexOutOfBoundsException, ParseException {
        TaskRepository tr = new TaskRepository(new ArrayList<Task>());
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tasks.txt")))
        {
            tr = (TaskRepository) ois.readObject();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        tr.inform();
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
                case ("complete"):
                    new CompleteCommand(tr).execute();
                    break;
                case ("save"):
                    new SaveCommand(tr).execute();
                    break;
            }
            in = new Scanner(System.in);
            System.out.print("\nInput a command (or input 'help' for list of commands): ");
            command = in.nextLine();
        }
        in.close();
    }
}
