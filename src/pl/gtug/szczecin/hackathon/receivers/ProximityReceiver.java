package pl.gtug.szczecin.hackathon.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;
import pl.gtug.szczecin.R;

public class ProximityReceiver extends BroadcastReceiver {
    private final static String TAG = ProximityReceiver.class.getCanonicalName();

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean entering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);
        Log.d(TAG, "onReceive entering=" + entering);

        if (entering) {
            Toast.makeText(context, R.string.proximity_entered, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, R.string.proximity_leaved, Toast.LENGTH_LONG).show();
        }
    }

}
