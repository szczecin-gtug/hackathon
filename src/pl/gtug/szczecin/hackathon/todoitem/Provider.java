package pl.gtug.szczecin.hackathon.todoitem;

import java.util.List;

import pl.gtug.szczecin.hackathon.database.TodoItemDbHandler;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by Pawel Stawicki on 2/18/12 3:51 PM
 */
@Singleton
public class Provider {

	@Inject
	private TodoItemDbHandler todoItemHandler;
	
    public List<TodoItem> getItems() {
        return todoItemHandler.getTodosList();
    }

}
