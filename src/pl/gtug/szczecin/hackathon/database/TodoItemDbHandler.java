package pl.gtug.szczecin.hackathon.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import pl.gtug.szczecin.hackathon.database.generated.TodoItem;

import java.util.List;

@Singleton
public class TodoItemDbHandler {

	@Inject
	private DbHelper helper;

	public List<TodoItem> getTodosList() {
        List<TodoItem> allTodos = helper.getTodoItemDao().loadAll();

        return allTodos;
    }

//	public void updateDescription(String oldValue, String newValue) {
//		ContentValues updateValues = new ContentValues();
//		updateValues.put("description", newValue);
//		helper.getWritableDatabase().update(TODO_TABLE_NAME, updateValues, DESCRIPTION + "=" + oldValue, null);
//	}
//
//	public void updateDoneStatus(String description, boolean done) {
//		ContentValues updateValues = new ContentValues();
//		updateValues.put("is_done", done ? "Y" : "N");
//		helper.getWritableDatabase().update(TODO_TABLE_NAME, updateValues, DESCRIPTION + "=" + description, null);
//	}

    public TodoItem getTodoItem(long itemId) {
        return helper.getTodoItemDao().load(itemId);
    }

    public void updateTodoItem(TodoItem todoItem) {
        helper.getTodoItemDao().update(todoItem);
    }

    public long createNewTodoItem() {
        TodoItem todoItem = new TodoItem();
        long id = helper.getTodoItemDao().insert(todoItem);

        return id;
    }

    public void insert(TodoItem newTodoItem) {
        helper.getTodoItemDao().insert(newTodoItem);
    }
}
