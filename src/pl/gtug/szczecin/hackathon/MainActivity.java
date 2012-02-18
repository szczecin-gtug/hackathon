package pl.gtug.szczecin.hackathon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import pl.gtug.szczecin.R;

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

        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.todoitem, R.id.itemView, new String[] {
                "asdf", "111", "2222"
        });

        listView.setAdapter(adapter);
    }
}
