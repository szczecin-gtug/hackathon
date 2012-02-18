package pl.gtug.szczecin.hackathon;

/**
 * Created by Pawel Stawicki on 2/18/12 1:36 PM
 */
public class TodoItem {
    
    private String taskDescription;

    public TodoItem(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public String toString() {
        return taskDescription;
    }
}
