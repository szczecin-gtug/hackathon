package pl.gtug.szczecin.hackathon.helpers;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationHelper implements LocationListener {
    private static final String TAG = LocationHelper.class.getCanonicalName();

    private LocationManager locationManager;
    private Location location;
    private String provider;

    public LocationHelper(Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

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

    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    public void onProviderEnabled(String s) {
    }

    public void onProviderDisabled(String s) {
    }
}
