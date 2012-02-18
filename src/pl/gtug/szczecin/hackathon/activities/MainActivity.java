package pl.gtug.szczecin.hackathon.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import pl.gtug.szczecin.R;
import pl.gtug.szczecin.hackathon.TodoItem;

public class MainActivity extends Activity
{
    private static final String TAG = MainActivity.class.getCanonicalName();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView listView = (ListView) findViewById(R.id.tasksList);

        ListAdapter adapter = new ArrayAdapter<TodoItem>(this, R.layout.todoitem, R.id.itemView, new TodoItem[] {
                new TodoItem("asdf"),
                new TodoItem("asdfasdf"),
                new TodoItem("sdfasgherghwe Dupa")
        });

        listView.setAdapter(adapter);
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
