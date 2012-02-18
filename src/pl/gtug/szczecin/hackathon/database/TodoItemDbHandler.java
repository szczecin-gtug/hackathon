package pl.gtug.szczecin.hackathon.database;

import java.util.ArrayList;
import java.util.List;

import pl.gtug.szczecin.hackathon.todoitem.TodoItem;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TodoItemDbHandler {

	public static final String TODO_TABLE_NAME = "TODO ";
	
	public static final String TODO_TABLE_STRUCTURE = "(id INTEGER PRIMARY KEY, description VARCHAR, is_done VARCHAR) ";
	
	@Inject
	private DbHelper helper;
	
	@Inject
	private LocationDbHandler locationHelper;
	
	public List<TodoItem> getTodosList() {
		Cursor todos = helper.getDatabase().query(TODO_TABLE_NAME, null, null, null, null, null, "name");
		
		List<TodoItem> result = new ArrayList<TodoItem>();
		
		if (todos.getCount() > 0) {
			todos.moveToFirst();
			do {
				String description = todos.getString(todos.getColumnIndex("description"));
				TodoItem todo = new TodoItem(description);				
				todo.setDoneStatus(todos.getString(todos.getColumnIndex("is_done")).equalsIgnoreCase("y") ? true : false);
				todo.setLocation(locationHelper.getLocation(description));
				result.add(todo);
			} while (todos.moveToNext());
		}
		return result;
	}
	
	public TodoItem getTodo(SQLiteDatabase database ,String description) {
		Cursor todos = database.query(TODO_TABLE_NAME, null, null, null, null, null, "name");
		
		TodoItem result = new TodoItem(description);
		
		if (todos.getCount() > 0) {
			todos.moveToFirst();
			do {
				if (todos.getString(todos.getColumnIndex("name")).equals(description)) {
					result.setDoneStatus(todos.getString(todos.getColumnIndex("is_done")).equalsIgnoreCase("y") ? true : false);
					result.setLocation(locationHelper.getLocation(description));
					break;
				}
			} while (todos.moveToNext());
		}
		
		return result;
	}
	
	public void updateDescription(String oldValue, String newValue) {
		ContentValues updateValues = new ContentValues();
		updateValues.put("description", newValue);
		helper.getDatabase().update(TODO_TABLE_NAME, updateValues, "description=" + oldValue, null);
	}
	
	public void updateDoneStatus(String description, boolean done) {
		ContentValues updateValues = new ContentValues();
		updateValues.put("is_done", done ? "Y" : "N");
		helper.getDatabase().update(TODO_TABLE_NAME, updateValues, "description=" + description, null);
	}
	
}
