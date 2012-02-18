package pl.gtug.szczecin.hackathon.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import pl.gtug.szczecin.hackathon.todoitem.TodoItem;

import java.util.ArrayList;
import java.util.List;

import static pl.gtug.szczecin.hackathon.database.DbHelper.*;

@Singleton
public class TodoItemDbHandler {

	@Inject
	private DbHelper helper;
	
	@Inject
	private LocationDbHandler locationHelper;
	
	public List<TodoItem> getTodosList() {
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        Cursor todos = writableDatabase.query(TODO_TABLE_NAME, null, null, null, null, null, DESCRIPTION);
		
		List<TodoItem> result = new ArrayList<TodoItem>();
		
		if (todos.getCount() > 0) {
			todos.moveToFirst();
			do {
				String description = todos.getString(todos.getColumnIndex(DESCRIPTION));
				TodoItem todo = new TodoItem(description);
                int columnIndex = todos.getColumnIndex(DbHelper.IS_DONE);
                String isDoneValue = todos.getString(columnIndex);
                todo.setDoneStatus("y".equalsIgnoreCase(isDoneValue));
				todo.setLocation(locationHelper.getLocation(todos.getInt(todos.getColumnIndex(ID))));
				result.add(todo);
			} while (todos.moveToNext());
		}
		return result;
	}
	
	public TodoItem getTodo(SQLiteDatabase database ,String description) {
		Cursor todos = database.query(TODO_TABLE_NAME, null, null, null, null, null, DESCRIPTION);

		TodoItem result = new TodoItem(description);
		
		if (todos.getCount() > 0) {
			todos.moveToFirst();
			do {
				if (todos.getString(todos.getColumnIndex(DESCRIPTION)).equals(description)) {
					result.setDoneStatus(todos.getString(todos.getColumnIndex(IS_DONE)).equalsIgnoreCase("y"));
                    result.setLocation(locationHelper.getLocation(todos.getInt(todos.getColumnIndex(ID))));
					break;
				}
			} while (todos.moveToNext());
		}
		
		return result;
	}
	
	public void updateDescription(String oldValue, String newValue) {
		ContentValues updateValues = new ContentValues();
		updateValues.put("description", newValue);
		helper.getWritableDatabase().update(TODO_TABLE_NAME, updateValues, DESCRIPTION + "=" + oldValue, null);
	}
	
	public void updateDoneStatus(String description, boolean done) {
		ContentValues updateValues = new ContentValues();
		updateValues.put("is_done", done ? "Y" : "N");
		helper.getWritableDatabase().update(TODO_TABLE_NAME, updateValues, DESCRIPTION + "=" + description, null);
	}
	
}
