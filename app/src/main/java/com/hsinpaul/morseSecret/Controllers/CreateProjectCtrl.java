package com.hsinpaul.morseSecret.Controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hsinpaul.morseSecret.R;
import com.hsinpaul.morseSecret.Models.Projects;

/**
 * Created by hsinpaul on 2014/8/23.
 */
public class CreateProjectCtrl extends DialogFragment {

    TextView projectName;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_create_project, null);

        projectName = (TextView)v.findViewById(R.id.projectName);

        builder.setTitle(R.string.morseCreate)
                .setView(v)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Projects projects = new Projects();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("title",projectName.getText().toString() );
                        projects.insert(contentValues);
                        ProjectListCtrl.showProjects();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
