package pl.gtug.szczecin.hackathon.activities;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.maps.*;
import pl.gtug.szczecin.R;

import java.util.List;

public class GeneralMapActivity extends MapActivity {
    private MapController mc;
    private MapView mapView;
    private TextView tv;
    private List<Overlay> mapOverlays;
    private GeoPoint WiZutPosition = new GeoPoint((int)(53.448749* 1E6), (int) (14.490976 * 1E6)) ;
    private MarkerLayer markerlayer;

    public static Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map);
        tv = (TextView) findViewById(R.id.tv1);
        mapView = (MapView) findViewById(R.id.map1);
        mapView.setBuiltInZoomControls(true);
        mc = mapView.getController();

        mapOverlays = mapView.getOverlays();
        mContext = this;

        centerOnUserPosition();
    }

    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

    private void centerOnUserPosition() {
        Drawable marker_blue = this.getResources().getDrawable(R.drawable.marker_green);
        markerlayer = new MarkerLayer(marker_blue);
        markerlayer.addOverlayItem( new OverlayItem(WiZutPosition, getString(R.string.you_are_here), "" ));
        mapOverlays.add(markerlayer);

        mc.setZoom(14);
        mc.setCenter(WiZutPosition);
    }
}
