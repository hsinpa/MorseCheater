package com.hsinpaul.morseSecret;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.hsinpaul.morseSecret.Controllers.ProjectListCtrl;
import com.hsinpaul.morseSecret.Models.Schema.Database;
import com.hsinpaul.morseSecret.R;


public class Home extends ActionBarActivity {
    public static SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ProjectListCtrl())
                    .commit();
        }

        //Connect to SQLITE
        Database MorseDB = new Database(getApplicationContext());
        db = MorseDB.getWritableDatabase();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#36175e")));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if ( id ==  android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeFragment(Fragment fragment, Bundle value) {

        // Create new fragment and transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    // Replace whatever is in the fragment_container view with this fragment,
    // and add the transaction to the back stack
            fragment.setArguments(value);
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);

    // Commit the transaction
            transaction.commit();
    }
}
