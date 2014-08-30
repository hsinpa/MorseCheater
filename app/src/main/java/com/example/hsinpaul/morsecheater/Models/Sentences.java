package com.example.hsinpaul.morsecheater.Models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hsinpaul.morsecheater.Home;
import com.example.hsinpaul.morsecheater.Models.Schema.MorseContract;

/**
 * Created by hsinpaul on 2014/8/27.
 */
public class Sentences  {

    public static SQLiteDatabase db = null;

    public Sentences() {
        db = Home.db;
    }

    public Cursor getSentences( int projectId ) {
        String q = "SELECT title, Sentences.*"+
                " FROM "+ MorseContract.SentenceEntry.TABLE_NAME +
                " LEFT JOIN Projects ON Sentences.project_id = Projects.id"+
                " WHERE Sentences.project_id = "+projectId;

        Cursor cursor = db.rawQuery(q, new String[]{});
        return cursor;
    }

    public Cursor getMorseCode() {
        String q = "SELECT *"+
                " FROM "+ MorseContract.MorseCodeEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(q, new String[]{});
        return cursor;
    }

    public Cursor getProjectDetails( int id ) {
        String q = "SELECT Projects.id AS projectId, title, Sentences.id AS sentenceId, sentence," +
                " morsecode, Sentences.last_update_time" +
                " FROM "+MorseContract.ProjectEntry.TABLE_NAME +
                " INNER JOIN Sentences ON Sentences.project_id = Projects.id";
        Cursor cursor = db.rawQuery(q, new String[]{});
        return cursor;
    }

    public void insert(ContentValues values) {
        db.insert(MorseContract.SentenceEntry.TABLE_NAME, null, values );
    }
}
