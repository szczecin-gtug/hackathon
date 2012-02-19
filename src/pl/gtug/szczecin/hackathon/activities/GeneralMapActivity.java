package pl.gtug.szczecin.hackathon.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.maps.*;
import pl.gtug.szczecin.R;
import pl.gtug.szczecin.hackathon.helpers.LocationHelper;

import java.util.List;

public class GeneralMapActivity extends MapActivity {
    private MapController mc;
    private MapView mapView;
    private Button centerMapBtn;

    private List<Overlay> mapOverlays;
    private GeoPoint WiZutPosition = new GeoPoint((int)(53.448749* 1E6), (int) (14.490976 * 1E6)) ;
    private GeoPoint jagiellonska = new GeoPoint((int)(53.433169* 1E6), (int) (14.533805 * 1E6)) ;
    private GeoPoint galeriaGryf= new GeoPoint((int)(53.385491* 1E6), (int) (14.66211 * 1E6)) ;
    private MarkerLayer markerlayer;

    private LocationHelper locHelper;

    public static Context mContext;
    private long itemId;
    private Location userSelectedLocation = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemId = getIntent().getLongExtra(ItemDetailsActivity.SELECTED_ITEM_ID, -1);
        //if no itemId was given, then show all markers for locations

        setContentView(R.layout.map);
        centerMapBtn = (Button) findViewById(R.id.center_map_btn);

        mapView = (MapView) findViewById(R.id.map1);
        mapView.setBuiltInZoomControls(true);
        mc = mapView.getController();
        mapOverlays = mapView.getOverlays();
        centerMapBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Location userPosition = locHelper.getLocation();
                if (userPosition != null )
                {
                    mc.setCenter(new GeoPoint((int)(userPosition.getLatitude() *1e6),(int)(userPosition.getLongitude() *1e6) ));
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
        markerlayer = new MarkerLayer(marker_blue,this, (this.itemId == -1) );
        markerlayer.addOverlayItem( new OverlayItem(jagiellonska, getString(R.string.you_are_here), "" ));
        markerlayer.addOverlayItem( new OverlayItem(WiZutPosition, getString(R.string.you_are_here), "" ));
        markerlayer.addOverlayItem( new OverlayItem(galeriaGryf, getString(R.string.you_are_here), "" ));
        mapOverlays.add(markerlayer);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    private void centerOnUserPosition() {


        mc.setZoom(14);

        Location userPosition = locHelper.getLocation();
        if (userPosition != null )
        {
            mc.setCenter(new GeoPoint((int)(userPosition.getLatitude() *1e6),(int)(userPosition.getLongitude() *1e6) ));
        }
        else
        {
            mc.setCenter(galeriaGryf);
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
