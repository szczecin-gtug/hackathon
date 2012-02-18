package pl.gtug.szczecin.hackathon;

import android.location.Location;

/**
 * Created by Pawel Stawicki on 2/18/12 1:36 PM
 */
public class TodoItem {
    
    private String taskDescription;
    
    private Location location;
    
    private boolean done = false;

    public TodoItem(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isDone() {
		return done;
	}

	public void setDoneStatus(boolean done) {
		this.done = done;
	}
	
	@Override
    public String toString() {
        return taskDescription + " " + getLocation();
    }
}
