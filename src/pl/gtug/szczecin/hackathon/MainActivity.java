package pl.gtug.szczecin.hackathon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import pl.gtug.szczecin.R;

public class MainActivity extends Activity
{
    private static final String TAG = MainActivity.class.getCanonicalName();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //test co
    }
}
