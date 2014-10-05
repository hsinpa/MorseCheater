package com.hsinpaul.morseSecret.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hsinpaul.morseSecret.R;
import com.hsinpaul.morseSecret.Models.Schema.MorseContract;
import com.hsinpaul.morseSecret.Models.Sentences;
import com.hsinpaul.morseSecret.Tools.MorseCodeConvertor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hsinpaul on 2014/9/2.
 */
public class EditPageCtrl extends Fragment {

    TextView title;
    Button playButton;
    Button saveButton;
    TextView morseCodeViewer;
    EditText morseCodeInput;
    Pattern inputPattern = Pattern.compile("[\\w +-=()!?_@,.]");

    public static Fragment newInstance(int sentenceId, int pos) {
        Bundle args = new Bundle();
        args.putInt("sentenceId", sentenceId);
        args.putInt("pos", pos);

        EditPageCtrl fragment = new EditPageCtrl();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.template_sentence, container,false);
        Bundle b = getArguments();
        final int sentenceId = b.getInt("sentenceId");
        int pos = b.getInt("pos") + 1;

        final ArrayList<String> data = getSentence(sentenceId);
        saveButton = (Button)v.findViewById(R.id.createButton);
        playButton = (Button)v.findViewById(R.id.vibrateTrial);
        morseCodeViewer = (TextView) v.findViewById(R.id.morseCode);
        morseCodeInput = (EditText) v.findViewById(R.id.morseCodeInput);
        title = (TextView)v.findViewById(R.id.detailTitle);

        title.setText(data.get(5) + " -  "+ pos );
        morseCodeViewer.setText(data.get(4));
        morseCodeInput.setText(data.get(2));

        //Morse Code syn converse
        morseCodeInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Matcher result = inputPattern.matcher(morseCodeInput.getText().toString());
                if (result.find()) {
                    ArrayList<String> converter = MorseCodeConvertor.charConvert(morseCodeInput.getText().toString());
                    morseCodeViewer.setText(converter.get(1));
                }
                if (morseCodeInput.getText().length() == 0) {
                    morseCodeViewer.setText("");
                }
            }
        });

        //Try Vibration
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> converter = MorseCodeConvertor.charConvert(morseCodeInput.getText().toString());
                MorseCodeConvertor.vibrate(getActivity(), converter.get(0), false);
            }
        });

        //Save Record
        saveButton.setText(R.string.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matcher result = inputPattern.matcher(morseCodeInput.getText().toString());

                if (result.find()) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(morseCodeInput.getWindowToken(), 0);

                    ArrayList<String> converter = MorseCodeConvertor.charConvert(morseCodeInput.getText().toString());

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MorseContract.SentenceEntry.SENTENCE, morseCodeInput.getText().toString());
                    contentValues.put(MorseContract.SentenceEntry.MORSECODE, converter.get(0));
                    contentValues.put(MorseContract.SentenceEntry.PREVIEWCODE, converter.get(1));
                    Sentences.update(contentValues, MorseContract.SentenceEntry._ID + "=" + sentenceId);
                    Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), R.string.wrongInput, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


    public ArrayList<String> getSentence(int sentenId) {
        Sentences sentences = new Sentences();
        ArrayList<String> singleList = new ArrayList<String>();

        Cursor cursor = sentences.getSentence(sentenId);
                    // do what ever you want here
        cursor.moveToPosition(0);

                    singleList.add(cursor.getString(cursor.getColumnIndex("id")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("last_update_time")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("sentence")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("morsecode")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("previewCode")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("title")));
        cursor.close();

        return singleList;
    }
}
