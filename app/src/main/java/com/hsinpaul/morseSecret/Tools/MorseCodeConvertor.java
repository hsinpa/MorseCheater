package com.hsinpaul.morseSecret.Tools;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Vibrator;
import android.widget.Toast;

import com.hsinpaul.morseSecret.R;
import com.hsinpaul.morseSecret.Models.Sentences;

import java.util.ArrayList;

/**
 * Created by hsinpaul on 2014/8/28.
 */
public class MorseCodeConvertor {


    public MorseCodeConvertor() {

    }

    public static ArrayList<String> charConvert(String sentence) {
        ArrayList<String> morseArray = new ArrayList<String>();
        String morseCodeString = "", literalString = "";

        char plainword[] = sentence.toUpperCase().toCharArray();
        int wordNum = plainword.length;

        Sentences sentences = new Sentences();
        Cursor morseCode = sentences.getMorseCode();

        for (int i = 0; i < wordNum; i++) {
            if (morseCode.moveToFirst()) {
                do {
                    if (morseCode.getString(1).equals(Character.toString(plainword[i]))) {

                        morseCodeString += morseCode.getString(2);
                        literalString += morseCode.getString(3);
                        if (i != wordNum-1) {
                            morseCodeString += 4;
                            literalString += " ";
                        }
                    }
                } while (morseCode.moveToNext());
            }
                if(Character.toString(plainword[i]).equals(" ")) {
                    //Remove the last character 4
                morseCodeString = morseCodeString.substring(0,morseCodeString.length()-1);
                morseCodeString += "5";
                literalString += "  ";
            }

        }

        morseArray.add(morseCodeString);
        morseArray.add(literalString);
        morseCode.close();

        return morseArray;
    }

    public static void stopVibrate(Context context) {
        Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mVibrator.cancel();
    }

    public static long[] patternConvert(String morseString, int wpmSpeed) {
        int dot = 200 / wpmSpeed; //type 1
        int dash = 500 / wpmSpeed; //type 2 ,Length of a Morse Code "dash" in milliseconds
        int short_gap = 200 / wpmSpeed; // type 3, Length of Gap Between dots/dashes
        int medium_gap = 500 / wpmSpeed; // type 4, Length of Gap Between Letters
        int long_gap = 1000 / wpmSpeed; // type 5, Length of Gap Between Words
        int sentence_gap = 2000 / wpmSpeed; // type 5, Length of Gap Between Words

        String[] morseCode =  morseString.split("");
        int morseLength = morseCode.length;

        long[] pattern = new long[morseLength];
        pattern[0] = 0;
        long[] types = {0, dot, dash, short_gap, medium_gap, long_gap, sentence_gap };

        for (int i = 1; i < morseLength; i++) {
            pattern[i] = types[Integer.parseInt(morseCode[i])];
        }
        return pattern;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void vibrate(Context context, String morseString, boolean isRepeat) {

        morseString = (isRepeat) ? morseString.substring(0, morseString.length()-1) : morseString;
        SharedPreferences prefs = context.getSharedPreferences(
                "com.example.hsinpa.morseCode", Context.MODE_PRIVATE);
        int wpmSpeed = Integer.parseInt(prefs.getString("wpm", "10"));

        long[] pattern = patternConvert(morseString, wpmSpeed);

// Only perform this pattern one time (-1 means "do not repeat")

        Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mVibrator.cancel();
        if (mVibrator.hasVibrator()) {
            if (isRepeat) {
                mVibrator.vibrate(pattern, 0);
            } else {
                mVibrator.vibrate(pattern, -1);
            }
        } else {
            Toast.makeText(context, R.string.ApiOutdated, Toast.LENGTH_SHORT).show();
        }
    }
}
