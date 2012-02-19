package pl.gtug.szczecin.hackathon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.inject.Inject;
import pl.gtug.szczecin.R;
import pl.gtug.szczecin.hackathon.database.TodoItemDbHandler;
import pl.gtug.szczecin.hackathon.database.generated.TodoItem;
import roboguice.activity.RoboActivity;

/**
 * Created by Pawel Stawicki on 2/18/12 3:17 PM
 */
public class ItemDetailsActivity extends RoboActivity {

    public static final String SELECTED_ITEM_ID = "selected item id";
    
    @Inject
    private TodoItemDbHandler todoItemDbHandler;
    
    private TodoItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.itemdetails);

        long itemId = getIntent().getLongExtra(SELECTED_ITEM_ID, -1);
        
        item = null;
        if (itemId >= 0) {
            TextView itemTitleView = (TextView) findViewById(R.id.itemTitle);
            item = todoItemDbHandler.getTodoItem(itemId);
            itemTitleView.setText(item.getDescription());
        }
        
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText itemTitle = (EditText) findViewById(R.id.itemTitle);
                String description = itemTitle.getText().toString();
                
                if (item == null) {
                    item = new TodoItem();
                }
                
                item.setDescription(description);
                
                if (item.getId() == null) {
                    todoItemDbHandler.insert(item);
                } else {
                    todoItemDbHandler.updateTodoItem(item);
                }

                Intent intent = new Intent(ItemDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
