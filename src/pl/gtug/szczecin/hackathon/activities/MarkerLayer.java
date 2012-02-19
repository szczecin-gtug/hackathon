package pl.gtug.szczecin.hackathon.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
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
    private final static String TAG = MarkerLayer.class.getCanonicalName();
    private ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();

    private long lastDownTime = -1;
    private long lastUpTime = -1;
    private final static int QUICK_TAP_MS = 200;
    private final static int DOUBLE_CLICK_MS = 450;

    private float touched_X;
    private float touched_Y;
    
    private int px_move_acceptance = 10;
    private boolean isSelectOnly ;
    private Context context;
    private OnLocationSetListener onLocationSetListener;

    public MarkerLayer(Drawable defaultMarker, boolean locationSelectOnly, Context context) {
        super(boundCenterBottom(defaultMarker));
        this.isSelectOnly = locationSelectOnly;
        this.context = context;
        populate();
    }

    @Override
    protected OverlayItem createItem(int i) {
        return overlayItems.get(i);
    }

    public void addOverlayItem(OverlayItem overlay) {
        overlayItems.add(overlay);
        populate();
    }

    @Override
    public int size() {
        return overlayItems.size();
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
            //coby nie obslugiwac double click'ow
            if ( currentTime - lastDownTime < QUICK_TAP_MS && currentTime - lastUpTime > DOUBLE_CLICK_MS) {
                if ( event.getX() - touched_X < px_move_acceptance && event.getX() - touched_X > -px_move_acceptance &&
                     event.getY() - touched_Y < px_move_acceptance && event.getY() - touched_Y > -px_move_acceptance ) {
                    GeoPoint geoPoint = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
                    Toast.makeText(context, geoPoint.getLatitudeE6() / 1E6 + "," + geoPoint.getLongitudeE6() / 1E6, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "New Point");
                    lastUpTime = currentTime;

                    // location will be returned to previous intent
                    if (this.isSelectOnly) {
                        if (onLocationSetListener != null) {
                            Location location = createLocation(geoPoint);
                            onLocationSetListener.onLocationSet(location);
                        }
                    } else {
                        //TODO: add location to DB
                        Location newLocation = this.createLocation(geoPoint);
                    }
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
        Location location = new Location(LocationManager.GPS_PROVIDER);
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

        showTestDialog(overlayItems.get(index));
        showTodoItemInfo(overlayItems.get(index));
        //TODO: Show existing location todo-list
        return true;
    }
    
    private void showTestDialog (OverlayItem item)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());

        dialog.setNeutralButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
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
//        mapActivity.showItemDetails(loc);
        //TODO: get ITEM info for location
    }
    
    public interface OnLocationSetListener {
        public void onLocationSet(Location location);
    }

    public void setOnLocationSetListener(OnLocationSetListener onLocationSetListener) {
        this.onLocationSetListener = onLocationSetListener;
    }
}
