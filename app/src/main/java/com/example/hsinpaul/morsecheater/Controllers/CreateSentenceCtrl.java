package com.example.hsinpaul.morsecheater.Controllers;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsinpaul.morsecheater.Models.Schema.MorseContract;
import com.example.hsinpaul.morsecheater.Models.Sentences;
import com.example.hsinpaul.morsecheater.R;
import com.example.hsinpaul.morsecheater.Tools.MorseCodeConvertor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hsinpaul on 2014/8/28.
 */
public class CreateSentenceCtrl extends Fragment {

    Button playButton;
    TextView morseCodeViewer;
    EditText morseCodeInput;
    Pattern inputPattern = Pattern.compile("[\\w +-=()!?_@,.]");

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.template_sentence, container, false);

        playButton = (Button)rootView.findViewById(R.id.sentence_play);
        morseCodeViewer = (TextView) rootView.findViewById(R.id.morseCode);
        morseCodeInput = (EditText) rootView.findViewById(R.id.morseCodeInput);
        Button createButton = (Button)rootView.findViewById(R.id.createButton);

        Bundle args = getArguments();
        final int projectId = args.getInt("projectId", 0);


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

        //Create Sentence
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Convert the sentence to morseCode before Insert
                    Matcher result = inputPattern.matcher(morseCodeInput.getText().toString());

                    if (result.find()) {
                        ArrayList<String> converter = MorseCodeConvertor.charConvert(morseCodeInput.getText().toString());

                        Sentences sentence = new Sentences();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MorseContract.SentenceEntry.SENTENCE, morseCodeInput.getText().toString());
                        contentValues.put(MorseContract.SentenceEntry.MORSECODE, converter.get(0));
                        contentValues.put(MorseContract.SentenceEntry.PREVIEWCODE, converter.get(1));
                        contentValues.put(MorseContract.SentenceEntry.PROJECT_ID, projectId);
                        sentence.insert(contentValues);
                        getFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getActivity(), R.string.wrongInput, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        return rootView;
    }

}
