import commands.AddCommand;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                    "\ntime: " + task.getTime() + "\n" + isCompleted +"\n");
        }
    }

    public static void main (String[] args) throws ParseException, IndexOutOfBoundsException {
        TaskRepository tr = new TaskRepository(new ArrayList<Task>());
        StringBuffer listOfCommands = new StringBuffer();
        listOfCommands.append("list of commands: \n\nhelp - show list of commands;\nadd - add new task;" +
                "\nshow - show any task (tasks);\nshow all - show full list of tasks;\nremove - remove any task;" +
                "\nclear - clear list of tasks;\ncomplete - to make any task completed;\nrename - rename any task" +
                "\nchange d - change description of any task\nchange t - change time of any task\ncopy - copy task (new task will be uncompleted)" +
                "\nsave - save file" +
                "\nexit - to finish app.\n");
        Scanner in = new Scanner(System.in);
        System.out.print("Input a command (or input 'help' for list of commands): ");
        String command = in.nextLine();
        while(!command.equals("exit")) {
            switch (command) {
                case ("help"):
                    System.out.print(listOfCommands);
                    break;
                case ("add"):
                    new AddCommand(tr).execute();
                    String name;
                    String timeInString;
                    Date time;
                    break;
                case ("show all"):
                    show(tr.getTasks(), "no tasks");
                    break;
                case ("show"):
                    in = new Scanner(System.in);
                    System.out.print("Input name of task (tasks) you want: ");
                    name = in.nextLine();
                    show(tr.getTasksByName(name), "no such tasks");
                    break;
                case ("remove"):
                    in = new Scanner(System.in);
                    System.out.print("Input name of task (tasks) you want to remove: ");
                    name = in.nextLine();
                    if (tr.getTasksByName(name).size() == 1) {
                        tr.removeTask(name);
                        break;
                    }
                    if (!tr.getTasksByName(name).isEmpty()) {
                        in = new Scanner(System.in);
                        System.out.print("Here more then one task with the same name, chose the number of one you need or input \"0\" if you want to remove all: ");
                        show(tr.getTasksByName(name), "");
                        int num = in.nextInt();
                        if (num == 0) {
                            tr.removeTask(name);
                        } else {
                            tr.removeTask(name, num);
                        }
                        break;
                    }
                    show(tr.getTasksByName(name), "no such tasks");
                    break;
                case ("rename"):
                    in = new Scanner(System.in);
                    System.out.print("Input name of task (tasks) you want to rename: ");
                    name = in.nextLine();
                    in = new Scanner(System.in);
                    System.out.print("Input new name of this task: ");
                    String newName = in.nextLine();
                    if (tr.getTasksByName(name).size() == 1) {
                        tr.getTasksByName(name).get(0).setName(newName);
                        break;
                    }
                    if (!tr.getTasksByName(name).isEmpty()) {
                        in = new Scanner(System.in);
                        System.out.print("Here more then one task with the same name, chose the number of one you need or input \"0\" if you want to rename all: ");
                        show(tr.getTasksByName(name), "");
                        int num = in.nextInt();
                        if (num == 0) {
                            for (Task task : tr.getTasksByName(name)) {
                                task.setName(newName);
                            }
                        } else {
                            tr.getTasksByName(name).get(num - 1).setName(newName);
                        }
                        break;
                    }
                    show(tr.getTasksByName(name), "no such tasks");
                    break;
                case ("copy"):
                    in = new Scanner(System.in);
                    System.out.print("Input name of task you want to copy: ");
                    name = in.nextLine();
                    in = new Scanner(System.in);
                    System.out.print("Input time for new task: ");
                    timeInString = in.nextLine();
                    time = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(timeInString);
                    if (tr.getTasksByName(name).isEmpty()) {
                        show(tr.getTasksByName(name), "no such tasks");
                        break;
                    }
                    Task task;
                    if (tr.getTasksByName(name).size() > 1) {
                        in = new Scanner(System.in);
                        System.out.print("Here more then one task with the same name, chose the number of one you need: ");
                        show(tr.getTasksByName(name), "");
                        task = tr.getTasksByName(name).get(in.nextInt() - 1);
                    } else {
                        task = tr.getTasksByName(name).get(0);
                    }
                    tr.addTask(new Task(task.getName(), task.getDescription(), time));
                    break;
            }
            in = new Scanner(System.in);
            System.out.print("\nInput a command (or input 'help' for list of commands): ");
            command = in.nextLine();
        }
        in.close();
    }


}
