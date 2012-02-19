package pl.gtug.szczecin.hackathon.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;


public class MarkerLayer extends ItemizedOverlay {

    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    private final static String TAG = MarkerLayer.class.getCanonicalName();

    private long lastDownTime = -1;
    private long lastUpTime = -1;
    private final static int QUICK_TAP_MS = 250;
    private final static int DOUBLE_CLICK_MS = 450;
    private  Context mContext;
    
    public MarkerLayer(Drawable defaultMarker, Context context) {
        super(boundCenterBottom(defaultMarker));
        mContext = context;
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
        }

        if (event.getAction() == MotionEvent.ACTION_UP) { //when user lifts his finger very quickly
            if ( currentTime - lastDownTime < QUICK_TAP_MS) {
                if (currentTime - lastUpTime > DOUBLE_CLICK_MS)  //coby nie obslugiwac double click'ow
                {
                    GeoPoint p = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
                    Toast.makeText(this.mContext,
                            p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6() /1E6 ,
                            Toast.LENGTH_SHORT)
                            .show();
                    Log.i(TAG, "New Point");
                    //TODO: create new location here

                    lastUpTime = currentTime;
                    return true;
                }
            }
        }
        return super.onTouchEvent(event,mapView );
    }

    /***
     * Handles user long tap on marker (longer than QUICK_TAP_MS) on map.
     * @param index
     * @return
     */
    @Override
    protected boolean onTap(int index) {
        Log.i(TAG, "Marker clicked");
        //TODO: Show existing location todo-list
        AlertDialog.Builder dialog = new AlertDialog.Builder(GeneralMapActivity.mContext);
        dialog.setTitle(mOverlays.get(index).getTitle());
        dialog.setMessage(mOverlays.get(index).getSnippet());

        dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            //on click listener on the alert box
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });

        dialog.show();
        return true;
    }


}