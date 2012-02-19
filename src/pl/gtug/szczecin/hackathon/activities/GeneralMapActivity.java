package pl.gtug.szczecin.hackathon.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import pl.gtug.szczecin.R;
import pl.gtug.szczecin.hackathon.database.DbHelper;
import pl.gtug.szczecin.hackathon.helpers.LocationHelper;

import java.util.List;

public class GeneralMapActivity extends MapActivity {
    private MapController mapController;
    private MapView mapView;
    private Button centerMapBtn;

    private List<Overlay> mapOverlays;
    private GeoPoint WiZutPosition = new GeoPoint((int)(53.448749* 1E6), (int) (14.490976 * 1E6)) ;
    private GeoPoint jagiellonska = new GeoPoint((int)(53.433169* 1E6), (int) (14.533805 * 1E6)) ;
    private GeoPoint galeriaGryf= new GeoPoint((int)(53.385491* 1E6), (int) (14.66211 * 1E6)) ;
    private MarkerLayer markerLayer;

    private LocationHelper locHelper;

    public static Context context;
    private long itemId;
    private Location userSelectedLocation = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemId = getIntent().getLongExtra(ItemDetailsActivity.SELECTED_ITEM_ID, -1);
        //if no itemId was given, then show all markers for locations

        setContentView(R.layout.map);

        mapView = (MapView) findViewById(R.id.map);
        mapView.setBuiltInZoomControls(true);
        mapController = mapView.getController();
        mapOverlays = mapView.getOverlays();

        centerMapBtn = (Button) findViewById(R.id.center_map_btn);
        centerMapBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Location userPosition = locHelper.getLocation();
                if (userPosition != null )
                {
                    mapController.setCenter(new GeoPoint((int) (userPosition.getLatitude() * 1e6), (int) (userPosition.getLongitude() * 1e6)));
                }
            }
        });

        locHelper = new LocationHelper(this);
        addMarkerOverlayWithLocations();

        centerOnUserPosition();
    }

    protected void addMarkerOverlayWithLocations()
    {
        Drawable marker_blue = this.getResources().getDrawable(R.drawable.marker_green);
        markerLayer = new MarkerLayer(marker_blue, (this.itemId == -1), getApplicationContext());
        markerLayer.addOverlayItem(new OverlayItem(jagiellonska, getString(R.string.you_are_here), ""));
        markerLayer.addOverlayItem(new OverlayItem(WiZutPosition, getString(R.string.you_are_here), ""));
        markerLayer.addOverlayItem(new OverlayItem(galeriaGryf, getString(R.string.you_are_here), ""));
        markerLayer.setOnLocationSetListener(new MarkerLayer.OnLocationSetListener() {
            @Override
            public void onLocationSet(Location location) {
                Intent intent = getIntent();
                intent.putExtra(DbHelper.LAT, location.getLatitude());
                intent.putExtra(DbHelper.LON, location.getLongitude());
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
        mapOverlays.add(markerLayer);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    private void centerOnUserPosition() {
        mapController.setZoom(14);

        Location userPosition = locHelper.getLocation();
        if (userPosition != null ) {
            mapController.setCenter(new GeoPoint((int) (userPosition.getLatitude() * 1e6), (int) (userPosition.getLongitude() * 1e6)));
        } else {
            mapController.setCenter(galeriaGryf);
        }
    }

    protected void showItemDetails ( Location location)
    {
        Intent intent = new Intent(GeneralMapActivity.this, ItemDetailsActivity.class);
        //TODO: tu sie moze wyjebac, bo potrzebuje uzyskac id na podstawie lokacji
        int id = 0;
        intent.putExtra(ItemDetailsActivity.SELECTED_ITEM_ID, id);
        startActivity(intent);
    }

    public void setSelectedLocation(Location location) {
        this.userSelectedLocation = location;
    }
}
