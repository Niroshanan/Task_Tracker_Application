
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    static TaskManager taskManager = TaskManager.getTaskManager();
    static Scanner scanner = new Scanner(System.in);
    static MyFileHandler myFileHandler = MyFileHandler.getFileHandler();
    static String fileName = "tasks.dat";

    public static void main(String[] args) {
        myFileHandler.readTasksFromFile(fileName);
        showMenu();
    }

    static void showMenu() {
        System.out.println("\n----------TASK TRACKER APPLICATION----------");
        System.out.println("1 - Add new Tasks \n2 - Show all Tasks \n3 - Save & Exit");
        System.out.print("Enter Input: ");
        String menuInputString = scanner.nextLine();
        int menuInput = 0;
        try {
            menuInput = Integer.parseInt(menuInputString);
        } catch (NumberFormatException e) {
            System.out.println("Input Can Not Be a String");
            showMenu();
        }
        switch (menuInput) {
            case 1:
                taskManager.addNewTask();
                showMenu();
                break;
            case 2:
                if (!taskManager.getTaskList().isEmpty()) {
                    viewAllTasks();
                } else {
                    System.out.println("Task List Is Empty\n");
                }
                showMenu();
                break;
            case 3:
                myFileHandler.saveTasksToFile(fileName, taskManager.getTaskList());
                System.out.println("Exited\n");
                exit(0);
            default:
                System.out.println("Invalid Input");
                showMenu();
        }
    }

    static void viewAllTasks() {

        taskManager.ListAllTasks();
        System.out.println("\n Please enter the Task ID to view and edit a task. Enter '0' to return to the Main Menu.");
        System.out.print("Enter Input: ");
        String taskInputString = scanner.nextLine();
        int taskInput = 0;
        try {
            taskInput = Integer.parseInt(taskInputString);
        } catch (NumberFormatException e) {
            System.out.println("Input Can Not Be a String");
            viewAllTasks();
        }

        switch (taskInput) {
            case 0:
                break;
            default:
                if (taskInput > 0 && taskInput <= taskManager.getTaskList().size()) {
                    taskManager.viewTaskById(taskInput);
                    editTask(taskInput);
                } else {
                    System.out.println("Invalid Input");
                    viewAllTasks();
                }
        }
    }

    private static void editTask(int taskInput) {
        System.out.println("0:Back \n1:Update \n2:delete");
        System.out.print("Enter Input: ");
        String editInputString = scanner.nextLine();
        int editInput = 0;
        try {
            editInput = Integer.parseInt(editInputString);
        } catch (NumberFormatException e) {
            System.out.println("Input Can Not Be a String");
            editTask(taskInput);
        }
        switch (editInput) {
            case 0:
                break;
            case 1:
                taskManager.updateTaskById(taskInput);
                break;
            case 2:
                taskManager.deleteTaskById(taskInput);
                showMenu();
                break;
            default:
                System.out.println("Invalid Input");
                taskManager.ListAllTasks();
                editTask(taskInput);
        }
    }
}