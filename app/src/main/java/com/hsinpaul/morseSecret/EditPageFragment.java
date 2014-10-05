package com.hsinpaul.morseSecret;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.hsinpaul.morseSecret.Controllers.EditPageCtrl;
import com.hsinpaul.morseSecret.Models.Projects;

import java.util.ArrayList;

/**
 * Created by hsinpaul on 2014/9/1.
 */
public class EditPageFragment extends ActionBarActivity {
    ViewPager mViewPager;
    public static ArrayList<ArrayList<String>> Sentences = new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        int sentencePos = i.getIntExtra("sentencePos", 0);
        int projectId = i.getIntExtra("projectId", 0);


        // Instantiate a ViewPager and a PagerAdapter.
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.sentence_edit);
        setContentView(mViewPager);

        final ArrayList<ArrayList<String>> sentencesList = showProjects(projectId);


        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {

                return sentencesList.size();
            }

            @Override
            public Fragment getItem(int pos) {

                int sentenceId = Integer.parseInt(sentencesList.get(pos).get(0));
                return EditPageCtrl.newInstance(sentenceId, pos);
                }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#36175e")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager.setCurrentItem(sentencePos);
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

    public ArrayList<ArrayList<String>> showProjects(int projectId) {
        ArrayList<ArrayList<String>> myList = new ArrayList<ArrayList<String>>();
        Projects projects = new Projects();

        Cursor cursor = projects.getProjectDetails(projectId);
            if (cursor.moveToFirst()) {
                do {
                    // do what ever you want here
                    ArrayList<String> singleList = new ArrayList<String>();
                    singleList.add(cursor.getString(cursor.getColumnIndex("id")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("last_update_time")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("sentence")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("morsecode")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("previewCode")));
                    myList.add(singleList);

                } while (cursor.moveToNext());
            }
            cursor.close();
        return myList;
    }
}
