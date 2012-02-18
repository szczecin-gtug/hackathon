package pl.gtug.szczecin.hackathon.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "hackathon";
	
	private static final int DATABASE_VERSION = 2;
	
	private static final String TASKS_TABLE_NAME = "TASKS ";
	
	private static final String TASKS_TABLE_STRUCTURE = "(id INTEGER PRIMARY KEY, name VARCHAR) ";
	
	private static final String TASK_TABLE_NAME = "TASK ";
	
	private static final String TASK_TABLE_STRUCTURE = "(id INTEGER PRIMARY KEY, task_name VARCHAR, value VARCHAR, is_done VARCHAR) ";
	
	private static final String LOCATION_TABLE_NAME = "LOCATION ";
	
	private static final String LOCATION_TABLE_STRUCTURE = "(id INTEGER PRIMARY KEY, task_name VARCHAR, alt FLOAT, lon FLOAT) ";
	
	private static final String CREATE_TABLE_STMT = "CREATE TABLE IF NOT EXISTS ";
	
	private static final String SELECT_STMT = "Select * from ";
	
	private static final String WHERE = " where ";
	
	private static final String FINISH_STMT = ";";

	private SQLiteDatabase database;
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		database = getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		createTables();
	}

	private void createTables() {
		createTable(TASKS_TABLE_NAME, TASKS_TABLE_STRUCTURE);
		createTable(TASK_TABLE_NAME, TASK_TABLE_STRUCTURE);
		createTable(LOCATION_TABLE_NAME, LOCATION_TABLE_STRUCTURE);
	}
	
	private void createTable(String tableName, String tableStructure) {
		database.execSQL(CREATE_TABLE_STMT + tableName + tableStructure + FINISH_STMT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
	public Cursor getTasks() {
		return database.query(TASKS_TABLE_NAME, null, null, null, null, null, "name");
	}
	
	public Cursor getTask(String name) {
		Cursor results = database.query(TASK_TABLE_NAME, null, null, null, null, null, "name");
		return null;
	}
}
