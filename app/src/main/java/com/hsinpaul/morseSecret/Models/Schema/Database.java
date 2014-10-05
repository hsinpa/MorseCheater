package com.hsinpaul.morseSecret.Models.Schema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hsinpaul.morseSecret.Models.Schema.MorseContract.MorseCodeEntry;
import com.hsinpaul.morseSecret.Models.Schema.MorseContract.ProjectEntry;
import com.hsinpaul.morseSecret.Models.Schema.MorseContract.SentenceEntry;

/**
 * Created by hsinpaul on 2014/8/25.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "morse.db";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProjectEntry.ProjectTable);
        db.execSQL(SentenceEntry.SentenceTable);
        db.execSQL(MorseCodeEntry.MorseCodeTable);
        db.execSQL(MorseCodeEntry.MorseCodeData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SentenceEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MorseCodeEntry.TABLE_NAME);
        onCreate(db);
    }
}
