package pl.gtug.szczecin.hackathon.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.inject.Inject;
import pl.gtug.szczecin.R;
import pl.gtug.szczecin.hackathon.todoitem.Provider;
import pl.gtug.szczecin.hackathon.todoitem.TodoItem;
import roboguice.activity.RoboActivity;

/**
 * Created by Pawel Stawicki on 2/18/12 3:17 PM
 */
public class ItemDetailsActivity extends RoboActivity {

    public static final String SELECTED_ITEM = "selected item";
    
    @Inject
    private Provider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.itemdetails);

        View itemDetails = findViewById(R.id.itemDetailsView);
        int position = getIntent().getIntExtra(SELECTED_ITEM, 0);

        TextView itemTitleView = (TextView) findViewById(R.id.itemTitle);
        
        TodoItem item = provider.getItems().get(position);

        itemTitleView.setText(item.getTaskDescription());
    }
}
