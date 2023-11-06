import Model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskManager {
    private static TaskManager taskManager;
    private static MyFileHandler myFileHandler = MyFileHandler.getFileHandler();
    static String fileName = "tasks.dat";
    Date currentDate = new Date();

    Scanner taskScanner = new Scanner(System.in);
    private TaskManager() {

    }
    public static TaskManager getTaskManager() {
        if (taskManager == null) {
            synchronized (TaskManager.class) {
                if (taskManager == null) {
                    taskManager = new TaskManager();
                }
            }
        }
        return taskManager;
    }
    private static List<Task> taskList = new ArrayList<>();

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addNewTask() {

        System.out.println("\nNEW TASK");
        System.out.print("Enter Title: ");
        String title = taskScanner.nextLine();
        System.out.print("Enter Description: ");
        String description = taskScanner.nextLine();
        System.out.print("Enter Due Date Format(dd/MM/yyyy) : ");
        String dateString = taskScanner.nextLine();
        Date dueDate = null;
        try {
            dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
            if (dueDate != null && dueDate.after(currentDate)) {
                taskList.add(new Task(title, description, dueDate));
                System.out.println("\nNew Task Added\n");
                myFileHandler.saveTasksToFile(fileName, taskManager.getTaskList());
            } else {
                System.out.println("Invalid due date. Please provide a future date.");
            }
        } catch (ParseException e) {
            System.out.println(e);
            System.out.println("Task insertion has failed");
        }
    }
    public void ListAllTasks(){
        sortByDueDate();
        System.out.println("\n----------All Task----------");
        System.out.println("Id \t Due Date \t Title");
        for (Task t : taskList) {
            int id = taskList.indexOf(t)+1;
            System.out.println(id + "\t " + t.getDueDateString()+ "\t " + t.getTitle());
        }
    }
    public void viewTaskById(int id){
        int index = id-1;
        System.out.println(taskList.get(index).toString());
    }
    public void deleteTaskById(int id){
        int index = id-1;
        taskList.remove(index);
        System.out.println("Task deleted");
        myFileHandler.saveTasksToFile(fileName, taskManager.getTaskList());
    }
    public void updateTaskById(int id){
        int index = id-1;
        System.out.print("Please enter the new title to update (Press Enter to keep the current title unchanged)");
        String updatedTitle = taskScanner.nextLine();
        System.out.print("Please enter the new description to update (Press Enter to keep the current description" +
                " unchanged)");
        String updatedDescription = taskScanner.nextLine();
        System.out.print("Please enter the new date to update (Press Enter to keep the current date unchanged)");
        String dateString = taskScanner.nextLine();
        Date dueDate = null;
        try {
            if(dateString != ""){
                dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
            }
            else {
                dueDate = taskList.get(index).getDueDate();
            }
            if (dueDate.after(currentDate)) {
                taskList.get(index).setTitle((updatedTitle != "") ? updatedTitle : taskList.get(index).getTitle());
                taskList.get(index).setDescription((updatedDescription != "") ? updatedDescription : taskList.get(index)
                        .getDescription());
                taskList.get(index).setDueDate(dueDate);
                System.out.println("Task updated");
                myFileHandler.saveTasksToFile(fileName, taskManager.getTaskList());
            } else {
                System.out.println("Invalid due date.");
            }

        } catch (ParseException e) {
            System.out.println(e);
            System.out.println("Task update has failed");
        }
    }
    public void sortByDueDate() {
        taskList.sort(Comparator.comparing(Task::getDueDate));
    }


}
