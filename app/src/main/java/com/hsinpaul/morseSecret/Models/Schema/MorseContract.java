package com.hsinpaul.morseSecret.Models.Schema;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hsinpaul on 2014/8/25.
 */
public class MorseContract  {
    public static final String CONTENT_AUTHORITY = "com.example.hsinpa.morsecheater.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class ProjectEntry implements BaseColumns {

        public static final String TABLE_NAME = "Projects";
        public static final String _ID= "id";
        public static final String TITLE = "title";
        public static final String LAST_UPDATE_TIME = "last_update_time";

        public static final String ProjectTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TITLE + " TEXT NOT NULL, " +
                LAST_UPDATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP "+
                ");";
    }

    public static final class SentenceEntry implements BaseColumns {
        public static final String TABLE_NAME = "Sentences";
        public static final String _ID= "id";
        public static final String SENTENCE = "sentence";
        public static final String MORSECODE = "morsecode";
        public static final String PREVIEWCODE = "previewCode";
        public static final String PROJECT_ID = "project_id";
        public static final String LAST_UPDATE_TIME = "last_update_time";

        public static final String SentenceTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                SENTENCE + " TEXT NOT NULL, " +
                MORSECODE + " TEXT NOT NULL, " +
                PREVIEWCODE + " TEXT NOT NULL, " +
                PROJECT_ID + " INTEGER NOT NULL, " +
                LAST_UPDATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP, "+
                " FOREIGN KEY (" + PROJECT_ID + ") REFERENCES " +
                ProjectEntry.TABLE_NAME + " (" + ProjectEntry._ID + ") );";
    }

    public static final class MorseCodeEntry implements BaseColumns {
        public static final String TABLE_NAME = "MorseCode";
        public static final String _ID= "id";
        public static final String CHARACTER = "character";
        public static final String CODE = "code";
        public static final String PREVIEWCODE = "previewCode";


        public static final String MorseCodeTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                CHARACTER + " TEXT NOT NULL UNIQUE, " +
                CODE + " TEXT NOT NULL UNIQUE, " +
                PREVIEWCODE + " TEXT NOT NULL UNIQUE " +
                ");";

        //Morse Code (1 = . &&  2 = -) RETRIVE FROM http://rosettacode.org/wiki/Talk:Morse_code
        public static final String MorseCodeData = "INSERT INTO " + TABLE_NAME +
                "("+CHARACTER+", "+CODE+", "+PREVIEWCODE+")" +
                " VALUES ( 'A', 132, '.-')," +
                "('B', 2313131, '-...')," +
                "('C', 2313231, '-.-.')," +
                "('D', 23131, '-..')," +
                "('E', 1, '.')," +
                "('F', 1313231, '..-.')," +
                "('G', 23231, '--.')," +
                "('H', 1313131, '....')," +
                "('I', 131, '..')," +
                "('J', 1323232, '.---')," +
                "('K', 23132, '-.-')," +
                "('L', 1323131, '.-..')," +
                "('M', 232, '--')," +
                "('N', 231, '-.')," +
                "('O', 23232, '---')," +
                "('P', 1323231, '.--.')," +
                "('Q', 2323132, '--.-')," +
                "('R', 13231, '.-.')," +
                "('S', 13131, '...' )," +
                "('T', 2 ,'-')," +
                "('U', 13132, '..-' )," +
                "('V', 1313132, '...-')," +
                "('W', 13232, '.--')," +
                "('X', 2313132, '-..-')," +
                "('Y', 2313232, '-.--')," +
                "('Z', 2323131, '--..')," +
                "('0', 232323232, '-----')," +
                "('1', 132323232, '.----')," +
                "('2', 131323232, '..---')," +
                "('3', 131313232, '...--')," +
                "('4', 131313132, '....-')," +
                "('5', 131313131, '.....')," +
                "('6', 231313131, '-....')," +
                "('7', 232313131, '--...')," +
                "('8', 232323131, '---..')," +
                "('9', 232323231, '----.')," +
                "(':', 23232313131, '---...')," +
                "(',', 23231313232, '--..--')," +
                "('-', 23131313132, '-....-')," +
                "('(', 231323231, '-.--.')," +
                "('.', 13231323132, '.-.-.-')," +
                "('?', 13132323131, '..--..')," +
                "(';', 23132313231, '-.-.-.')," +
                "('/', 231313231, '-..-.')," +
                "('_', 13132323132, '..--.-')," +
                "('$', 1313132313132, '...-..-')," +
                "('!', 13231323131, '.-.-..')," +
                "(')', 23132323132, '-.--.-')," +
                "('=', 231313132, '-...-')," +
                "('@', 13232313231, '.--.-.')," +
                "('+', 132313231, '.-.-.')";
    }
}
