package pl.gtug.szczecin.hackathon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.inject.Inject;
import pl.gtug.szczecin.R;
import pl.gtug.szczecin.hackathon.todoitem.Provider;
import pl.gtug.szczecin.hackathon.todoitem.TodoItem;
import roboguice.activity.RoboActivity;

public class MainActivity extends RoboActivity
{
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Inject
    Provider provider;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView listView = (ListView) findViewById(R.id.tasksList);

        ListAdapter adapter = new ArrayAdapter<TodoItem>(this, R.layout.todoiteminlist, R.id.itemView, provider.getItems());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Click ListItem Number " + position + " id: " + id, Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(MainActivity.this, ItemDetailsActivity.class);
                intent.putExtra(ItemDetailsActivity.SELECTED_ITEM, position);
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

        switch (item.getItemId()) {
            case R.id.menuExit:
                Log.i(TAG, "Pressed menu item: exit");
                finish();
                return true;

            case R.id.menuPreferences:
                Log.i(TAG, "Pressed menu item: preferences");
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);
                return true;
        }

        return onOptionsItemSelected(item);
    }

}
