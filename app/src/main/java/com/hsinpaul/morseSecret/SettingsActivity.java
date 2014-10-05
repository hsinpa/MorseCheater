package com.hsinpaul.morseSecret;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.hsinpaul.morseSecret.R;

/**
 * Created by hsinpaul on 2014/9/8.
 */
public class SettingsActivity extends PreferenceActivity {
    ListPreference setWpm;
    SharedPreferences prefs;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        prefs = getApplicationContext().getSharedPreferences(
                "com.example.hsinpa.morseCode", Context.MODE_PRIVATE);


        setWpm = (ListPreference) findPreference("wpm");

        setWpm.setSummary(prefs.getString("wpm", "1") + "0 WPMs");

        setWpm.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();

                setWpm.setSummary(stringValue + "0 WPMs");
                prefs.edit().putString("wpm", stringValue).apply();
                return false;
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id ==  android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
