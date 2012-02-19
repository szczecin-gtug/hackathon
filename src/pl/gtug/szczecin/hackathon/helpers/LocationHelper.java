package pl.gtug.szczecin.hackathon.helpers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.google.inject.Inject;
import pl.gtug.szczecin.hackathon.Constants;

public class LocationHelper implements LocationListener {
    private static final String TAG = LocationHelper.class.getCanonicalName();

    private LocationManager locationManager;
    private Location location;
    private String provider;
    private Context context;

    @Inject
    public LocationHelper(Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.context = context;

        // best provider
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);
        Log.d(TAG, "provider = " + provider);

        locationManager.requestLocationUpdates(provider, 0, 0, this);
        location = locationManager.getLastKnownLocation(provider);
    }

    public Location getLocation() {
        return location;
    }

    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        this.location = location;
    }

    public void addProximityAlert(double latitude, double longitude) {
        Intent intent = new Intent(Constants.ACTION_PROXIMITY_ALERT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        locationManager.addProximityAlert(latitude, longitude, Constants.PROXIMITY_ALERT_RADIUS,
                Constants.PROXIMITY_ALERT_EXPIRATION, pendingIntent);
    }

    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    public void onProviderEnabled(String s) {
    }

    public void onProviderDisabled(String s) {
    }
}
