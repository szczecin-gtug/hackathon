package pl.gtug.szczecin.hackathon.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;


public class MarkerLayer extends ItemizedOverlay {

    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    public MarkerLayer(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
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
    @Override
    protected boolean onTap(int index) {
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
        return super.onTap(index);
    }


}