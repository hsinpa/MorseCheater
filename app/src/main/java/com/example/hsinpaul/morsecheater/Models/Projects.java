package com.example.hsinpaul.morsecheater.Models;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.hsinpaul.morsecheater.Home;
import com.example.hsinpaul.morsecheater.Models.Schema.Database;
import com.example.hsinpaul.morsecheater.Models.Schema.MorseContract;

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
                " FROM "+MorseContract.ProjectEntry.TABLE_NAME +
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
