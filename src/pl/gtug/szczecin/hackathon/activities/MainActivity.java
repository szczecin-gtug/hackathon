package pl.gtug.szczecin.hackathon.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.inject.Inject;
import pl.gtug.szczecin.R;
import pl.gtug.szczecin.hackathon.database.DbHelper;
import pl.gtug.szczecin.hackathon.database.generated.TodoItem;
import roboguice.activity.RoboActivity;

import java.util.List;

import static pl.gtug.szczecin.hackathon.activities.ItemDetailsActivity.SELECTED_ITEM_ID;

public class MainActivity extends RoboActivity
{
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Inject
    private DbHelper dbHelper;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        initiateListView();

        initAddItemButton();
    }

    private void initAddItemButton() {
        Button addItem = (Button) findViewById(R.id.addItemButton);
        addItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ItemDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initiateListView() {
        ListView listView = (ListView) findViewById(R.id.tasksList);

        final List<TodoItem> todosList = dbHelper.getTodoItemDao().loadAll();
        ListAdapter adapter = new ArrayAdapter<TodoItem>(this, R.layout.todoiteminlist, R.id.itemView, todosList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=getLayoutInflater();
                View row = inflater.inflate(R.layout.todoiteminlist, parent, false);
                TextView label = (TextView)row.findViewById(R.id.itemView);
                TodoItem todoItem = todosList.get(position);

                label.setText(todoItem.getDescription());

                return row;
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Click ListItem Number " + position + " id: " + id, Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(MainActivity.this, ItemDetailsActivity.class);
                intent.putExtra(SELECTED_ITEM_ID, todosList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    /**
     * Podpięcie menu aplikacji pod przycisk telefonu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Obsługa elementów menu
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menuMap:
                Log.i(TAG, "Pressed menu item: map");
                intent = new Intent(this, GeneralMapActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuPreferences:
                Log.i(TAG, "Pressed menu item: preferences");
                intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuAbout:
                Log.i(TAG, "Pressed menu item: about");
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(getString(R.string.about));
                alertDialog.setMessage(getString(R.string.about_content));
                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
                return true;

            case R.id.menuExit:
                Log.i(TAG, "Pressed menu item: exit");
                finish();
                return true;
        }

        return onOptionsItemSelected(item);
    }

}
