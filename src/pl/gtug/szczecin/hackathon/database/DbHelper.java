package pl.gtug.szczecin.hackathon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "hackathon";
	
	private static final int DATABASE_VERSION = 2;
	
	private static final String CREATE_TABLE_STMT = "CREATE TABLE IF NOT EXISTS ";
	
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
		createTable(TodoItemDbHandler.TODO_TABLE_NAME, TodoItemDbHandler.TODO_TABLE_STRUCTURE);
		createTable(LocationDbHandler.LOCATION_TABLE_NAME, LocationDbHandler.LOCATION_TABLE_STRUCTURE);
	}
	
	private void createTable(String tableName, String tableStructure) {
		database.execSQL(CREATE_TABLE_STMT + tableName + tableStructure + FINISH_STMT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
}
