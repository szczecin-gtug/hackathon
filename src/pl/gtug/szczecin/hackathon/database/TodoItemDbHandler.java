package pl.gtug.szczecin.hackathon.database;

import java.util.ArrayList;
import java.util.List;

import pl.gtug.szczecin.hackathon.TodoItem;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TodoItemDbHandler {

	public static final String TODO_TABLE_NAME = "TODO ";
	
	public static final String TODO_TABLE_STRUCTURE = "(id INTEGER PRIMARY KEY, description VARCHAR, is_done VARCHAR) ";
	
	public List<TodoItem> getTodosList(SQLiteDatabase database) {
		Cursor todos = database.query(TODO_TABLE_NAME, null, null, null, null, null, "name");
		
		List<TodoItem> result = new ArrayList<TodoItem>();
		
		if (todos.getCount() > 0) {
			todos.moveToFirst();
			do {
				String description = todos.getString(todos.getColumnIndex("description"));
				TodoItem todo = new TodoItem(description);				
				todo.setDoneStatus(todos.getString(todos.getColumnIndex("is_done")).equalsIgnoreCase("y") ? true : false);
				todo.setLocation(LocationDbHandler.getLocation(database, description));
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
					result.setLocation(LocationDbHandler.getLocation(database, description));
					break;
				}
			} while (todos.moveToNext());
		}
		
		return result;
	}
	
	public void updateDescription(SQLiteDatabase database, String oldValue, String newValue) {
		ContentValues updateValues = new ContentValues();
		updateValues.put("description", newValue);
		database.update(TODO_TABLE_NAME, updateValues, "description=" + oldValue, null);
	}
	
	
	
	public void updateDoneStatus(SQLiteDatabase database, String description, boolean done) {
		ContentValues updateValues = new ContentValues();
		updateValues.put("is_done", done ? "Y" : "N");
		database.update(TODO_TABLE_NAME, updateValues, "description=" + description, null);
	}
	
}
