package pl.gtug.szczecin.hackathon.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import pl.gtug.szczecin.R;

import java.util.ArrayList;

public class MarkerLayer extends ItemizedOverlay {

    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    private final static String TAG = MarkerLayer.class.getCanonicalName();

    private long lastDownTime = -1;
    private long lastUpTime = -1;
    private final static int QUICK_TAP_MS = 200;
    private final static int DOUBLE_CLICK_MS = 450;
    private GeneralMapActivity mapActivity;

    private float touched_X;
    private float touched_Y;
    
    private int px_move_acceptance = 10;
    
    public MarkerLayer(Drawable defaultMarker,GeneralMapActivity _mapActivity) {
        super(boundCenterBottom(defaultMarker));
        mapActivity = _mapActivity;
        populate();
    }

    @Override
    protected OverlayItem createItem(int i) {
        return mOverlays.get(i);
    }

    public void addOverlayItem(OverlayItem overlay) {
        mOverlays.add(overlay);
        populate();
    }

    @Override
    public int size() {
        return mOverlays.size();
    }

    /***
     * Handles user quick tap (quicker than QUICK_TAP_MS) on mapView.
     * @param event
     * @param mapView
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event, MapView mapView)
    {
        long currentTime = System.currentTimeMillis();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastDownTime = currentTime;
            touched_X = event.getX();
            touched_Y = event.getY();
        }

        else if (event.getAction() == MotionEvent.ACTION_UP) { //when user lifts his finger very quickly
            if ( currentTime - lastDownTime < QUICK_TAP_MS && currentTime - lastUpTime > DOUBLE_CLICK_MS) //coby nie obslugiwac double click'ow
            {
                if ( event.getX() - touched_X < px_move_acceptance &&  event.getX() - touched_X > -px_move_acceptance   &&
                        event.getY() - touched_Y <px_move_acceptance &&  event.getY() - touched_Y > -px_move_acceptance )
                {
                    GeoPoint p = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
                    Toast.makeText(this.mapActivity,
                            p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6() /1E6 ,
                            Toast.LENGTH_SHORT)
                            .show();
                    Log.i(TAG, "New Point");
                    lastUpTime = currentTime;

                    Location newLocation = this.createLocation(p);
                    //TODO: add location to DB

                    return true;
                }
            }
        }
        return super.onTouchEvent(event,mapView );
    }

    protected Location createLocation(GeoPoint point)
    {
        float latitude = point.getLatitudeE6() / 1E6F;
        float longitude = point.getLongitudeE6() / 1E6F;
        Location location = new Location(this.mapActivity.getString(R.string.app_name));
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    /***
     * Handles user long tap on marker (longer than QUICK_TAP_MS) on map.
     * @param index
     * @return
     */
    @Override
    protected boolean onTap(int index) {
        Log.i(TAG, "Marker clicked");

        showTestDialog(mOverlays.get(index));
        showTodoItemInfo (mOverlays.get(index));
        //TODO: Show existing location todo-list
        return true;
    }
    
    private void showTestDialog (OverlayItem item)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(GeneralMapActivity.mContext);
        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());

        dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            //on click listener on the alert box
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
    
    private void showTodoItemInfo(OverlayItem marker)
    {
        Location loc = createLocation(marker.getPoint());
        mapActivity.showItemDetails (loc);
        //TODO: get ITEM info for location
    }


}
