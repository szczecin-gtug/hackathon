package pl.gtug.szczecin.hackathon;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import pl.gtug.szczecin.R;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

}
