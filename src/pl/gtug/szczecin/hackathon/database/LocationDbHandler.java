//package pl.gtug.szczecin.hackathon.database;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.location.Location;
//
//import com.google.inject.Inject;
//
//public class LocationDbHandler {
//
//	public static final String LOCATION_TABLE_NAME = "LOCATION ";
//
//    private static final String ID = "id";
//    private static final String LAT = "lat";
//    private static final String LON = "lon";
//    public static final String LOCATION_TABLE_STRUCTURE = "(" +
//            ID + " INTEGER PRIMARY KEY, " +
//            LAT + " DOUBLE," +
//            LON + " DOUBLE) ";
//
//	@Inject
//	private DbHelper helper;
//
//	public Location getLocation(long itemId) {
//		Location result = null;
//
//		Cursor locations = helper.getReadableDatabase().query(LOCATION_TABLE_NAME, null, "id=?", new String[] {Long.toString(itemId)}, null, null, null);
//
//		if (locations.getCount() > 0) {
//			locations.moveToFirst();
////			do {
////				if (locations.getString(locations.getColumnIndex("name")).equals(todoDesc)) {
//					result = new Location("location");
//					result.setLatitude(locations.getDouble(locations.getColumnIndex(LAT)));
//					result.setLongitude(locations.getDouble(locations.getColumnIndex(LON)));
////					break;
////				}
////			} while (locations.moveToNext());
//		}
//
//		return result;
//	}
//
//	public void updateLocation(String description, Location newLocation) {
//		ContentValues updateValues = new ContentValues();
//		updateValues.put("alt", newLocation.getAltitude());
//		updateValues.put(LON, newLocation.getLongitude());
//		helper.getWritableDatabase().update(LOCATION_TABLE_NAME, updateValues, "description=" + description, null);
//	}
//
//}
