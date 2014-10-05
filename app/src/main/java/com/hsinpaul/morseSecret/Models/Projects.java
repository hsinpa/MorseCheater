package com.hsinpaul.morseSecret.Models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hsinpaul.morseSecret.Home;
import com.hsinpaul.morseSecret.Models.Schema.MorseContract;

/**
 * Created by hsinpaul on 2014/8/25.
 */
public class Projects {
    public static SQLiteDatabase db = null;

    public Projects() {
        db = Home.db;
    }

    public Cursor getProjects( ) {
        String q = "SELECT Projects.*, COUNT(Sentences.id) AS sentenceNum"+
                " FROM "+ MorseContract.ProjectEntry.TABLE_NAME +
                " LEFT JOIN Sentences ON Sentences.project_id = Projects.id"+
                " GROUP BY Projects.id";

        Cursor cursor = db.rawQuery(q, new String[]{});
        return cursor;
    }

    public Cursor getProjectDetails( int projectId ) {

        String q = "SELECT Sentences.*, title" +
                " FROM " + MorseContract.SentenceEntry.TABLE_NAME +
                " INNER JOIN Projects ON Sentences.project_id = Projects.id" +
                " WHERE Sentences.project_id = " + projectId;

        Cursor cursor = db.rawQuery(q, new String[]{});
        return cursor;

    }

    public void insert(ContentValues values) {
        db.insert(MorseContract.ProjectEntry.TABLE_NAME, null, values );
    }
}
