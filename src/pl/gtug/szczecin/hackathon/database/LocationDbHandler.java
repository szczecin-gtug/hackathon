package pl.gtug.szczecin.hackathon.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

public class LocationDbHandler {

	public static final String LOCATION_TABLE_NAME = "LOCATION ";
		
	public static final String LOCATION_TABLE_STRUCTURE = "(id INTEGER PRIMARY KEY, description VARCHAR, lat DOUBLE, lon DOUBLE) ";
	
	public static Location getLocation(SQLiteDatabase database, String todoDesc) {
		Location result = null;
		
		Cursor locations = database.query(LOCATION_TABLE_NAME, null, null, null, null, null, "name");
		
		if (locations.getCount() > 0) {
			locations.moveToFirst();
			do {
				if (locations.getString(locations.getColumnIndex("name")).equals(todoDesc)) {
					result = new Location("location");
					result.setLatitude(locations.getDouble(locations.getColumnIndex("lat")));
					result.setLongitude(locations.getDouble(locations.getColumnIndex("lon")));
					break;
				}
			} while (locations.moveToNext());
		}
		
		return result;
	}
	
	public static void updateLocation(SQLiteDatabase database, String description, Location newLocation) {
		ContentValues updateValues = new ContentValues();
		updateValues.put("alt", newLocation.getAltitude());
		updateValues.put("lon", newLocation.getLongitude());
		database.update(LOCATION_TABLE_NAME, updateValues, "description=" + description, null);
	}
	
}
