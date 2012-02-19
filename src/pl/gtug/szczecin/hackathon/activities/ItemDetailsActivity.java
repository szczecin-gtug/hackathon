package pl.gtug.szczecin.hackathon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.inject.Inject;
import pl.gtug.szczecin.R;
import pl.gtug.szczecin.hackathon.database.DbHelper;
import pl.gtug.szczecin.hackathon.database.generated.Location;
import pl.gtug.szczecin.hackathon.database.generated.TodoItem;
import pl.gtug.szczecin.hackathon.helpers.LocationHelper;
import roboguice.activity.RoboActivity;

/**
 * Created by Pawel Stawicki on 2/18/12 3:17 PM
 */
public class ItemDetailsActivity extends RoboActivity {

    public static final String SELECTED_ITEM_ID = "selected item id";
    
    @Inject private DbHelper dbHelper;
    @Inject private LocationHelper locationHelper;
    
    private TodoItem item;
    private int requestCode = 0;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.itemdetails);

        final long itemId = getIntent().getLongExtra(SELECTED_ITEM_ID, -1);
        
        item = null;
        if (itemId >= 0) {
            TextView itemTitleView = (TextView) findViewById(R.id.itemTitle);
            item = dbHelper.getTodoItemDao().load(itemId);
            itemTitleView.setText(item.getDescription());
        }

        Button setLocationBtn = (Button) findViewById(R.id.setLocationButton);
        setLocationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailsActivity.this, GeneralMapActivity.class);
                intent.putExtra(SELECTED_ITEM_ID, itemId);
                startActivityForResult(intent, requestCode);
            }
        });
        
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText itemTitle = (EditText) findViewById(R.id.itemTitle);
                String description = itemTitle.getText().toString();
                
                if (item == null) {
                    item = new TodoItem();
                }
                
                if (location != null) {
                    Long locationId = location.getId();
                    if (locationId != null) {
                        dbHelper.getLocationDao().update(location);
                    } else {
                        locationId = dbHelper.getLocationDao().insert(location);
                    }
                    
                    item.setLocationId(locationId);
                }
                
                item.setDescription(description);

                if (item.getId() == null) {
                    dbHelper.getTodoItemDao().insert(item);
                } else {
                    dbHelper.getTodoItemDao().update(item);
                }

                Intent intent = new Intent(ItemDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            double latitude = data.getDoubleExtra(DbHelper.LAT, 0);
            double longitude = data.getDoubleExtra(DbHelper.LON, 0);
            
            location = new Location();
            location.setLat(latitude);
            location.setLon(longitude);
            
            locationHelper.addProximityAlert(latitude, longitude);
        }
    }
}
