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

    public Cursor getSentence( int id ) {
        String q = "SELECT Sentences.*, title"+
                " FROM " + MorseContract.SentenceEntry.TABLE_NAME +
                " LEFT JOIN "+ MorseContract.ProjectEntry.TABLE_NAME +" ON "+ MorseContract.ProjectEntry.TABLE_NAME +".id = "+ MorseContract.SentenceEntry.TABLE_NAME+".project_id" +
                " WHERE " + MorseContract.SentenceEntry.TABLE_NAME +".id = " + id;

        return db.rawQuery(q, new String[]{});
    }

    public Cursor getMorseCode() {
        String q = "SELECT *"+
                " FROM "+ MorseContract.MorseCodeEntry.TABLE_NAME;

        return db.rawQuery(q, new String[]{});
    }

    public static void update(ContentValues values, String condition ) {
        db.update(MorseContract.SentenceEntry.TABLE_NAME, values, condition, null);
    }

    public void insert(ContentValues values) {
        db.insert(MorseContract.SentenceEntry.TABLE_NAME, null, values );
    }
}
