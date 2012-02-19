package pl.gtug.szczecin.hackathon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.google.inject.Inject;
import pl.gtug.szczecin.hackathon.database.generated.DaoMaster;
import pl.gtug.szczecin.hackathon.database.generated.DaoSession;
import pl.gtug.szczecin.hackathon.database.generated.TodoItemDao;

public class DbHelper extends DaoMaster.DevOpenHelper {

	public static final String DATABASE_NAME = "hackathon";
    public static final String TODO_ITEM = "TodoItem";
    public static final String LOCATION = "Location";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String DESCRIPTION = "description";
    public static final String IS_DONE = "is_done";

    @Inject
    private static Context context;

    public DbHelper() {
		super(context, DATABASE_NAME, null);
	}

    public TodoItemDao getTodoItemDao() {
        SQLiteDatabase db = getWritableDatabase();

        DaoMaster master = new DaoMaster(db);
        DaoSession session = master.newSession();
        TodoItemDao todoItemDao = session.getTodoItemDao();

        return todoItemDao;
    }

}
