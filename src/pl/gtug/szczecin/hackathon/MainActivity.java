package pl.gtug.szczecin.hackathon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import pl.gtug.szczecin.R;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView listView = (ListView) findViewById(R.id.tasksList);

        ListAdapter adapter = new ArrayAdapter<TodoItem>(this, R.layout.todoiteminlist, R.id.itemView, new TodoItem[] {
                new TodoItem("asdf"),
                new TodoItem("asdfasdf"),
                new TodoItem("sdfasgherghwe Dupa")
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		Toast.makeText(getApplicationContext(), "Click ListItem Number " + position + " id: " + id, Toast.LENGTH_LONG)
        			.show();
        	}
        });
    }

}
