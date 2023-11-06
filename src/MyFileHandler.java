import Model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFileHandler {
    private static MyFileHandler myFileHandler;

    private MyFileHandler() {

    }

    public static MyFileHandler getFileHandler() {
        if (myFileHandler == null) {
            synchronized (TaskManager.class) {
                if (myFileHandler == null) {
                    myFileHandler = new MyFileHandler();
                }
            }
        }
        return myFileHandler;
    }

    public void saveTasksToFile(String filename, List<Task> taskList) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
            outputStream.writeObject(taskList);
            outputStream.close();
            System.out.println("Tasks saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTasksFromFile(String filename) {
        TaskManager taskManager = TaskManager.getTaskManager();
        File myFile = new File(filename);

        if (!myFile.exists()) {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (myFile.exists() && myFile.length() != 0) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename));
                ArrayList<Task> taskList = (ArrayList<Task>) objectInputStream.readObject();
                objectInputStream.close();
                for (Task t : taskList) {
                    taskManager.getTaskList().add(t);
                }
                System.out.println("File read success");
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
