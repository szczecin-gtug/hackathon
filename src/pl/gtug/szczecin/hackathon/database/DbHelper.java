package pl.gtug.szczecin.hackathon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "hackathon";
	
	private static final int DATABASE_VERSION = 2;
	
	private static final String CREATE_TABLE_STMT = "CREATE TABLE IF NOT EXISTS ";

    public static final String TODO_TABLE_NAME = "TODO ";

    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String IS_DONE = "is_done";
    public static final String TODO_TABLE_STRUCTURE = "(" +
            ID + " INTEGER PRIMARY KEY, " +
            DESCRIPTION + " VARCHAR, " +
            IS_DONE + " VARCHAR) ";
	
	private static final String FINISH_STMT = ";";

    @Inject
    private static Provider<Context> contextProvider;

	public DbHelper() {
		super(contextProvider.get(), DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);

        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, "todo1");
        db.insert(TODO_TABLE_NAME, null, values);
	}

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void createTables(SQLiteDatabase db) {
		createTable(db, TODO_TABLE_NAME, TODO_TABLE_STRUCTURE);
		createTable(db, LocationDbHandler.LOCATION_TABLE_NAME, LocationDbHandler.LOCATION_TABLE_STRUCTURE);
	}
	
	private void createTable(SQLiteDatabase db, String tableName, String tableStructure) {
		db.execSQL(CREATE_TABLE_STMT + tableName + tableStructure + FINISH_STMT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
	}

}
