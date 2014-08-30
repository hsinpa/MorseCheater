package com.example.hsinpaul.morsecheater.Controllers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hsinpaul.morsecheater.Home;
import com.example.hsinpaul.morsecheater.Models.Projects;
import com.example.hsinpaul.morsecheater.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hsinpaul on 2014/8/23.
 */
public class ProjectListCtrl extends Fragment {

    public static ListView listView;
    public static ArrayList<ArrayList<String>> myList = new ArrayList<ArrayList<String>>();
    public static String[] colors;
    public static TextView emptyText;

    public ProjectListCtrl() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.projectlist, container, false);

        colors = getResources().getStringArray(R.array.letterColor);
        Button button= (Button) rootView.findViewById(R.id.createButton);
        emptyText = (TextView) rootView.findViewById(R.id.emptyData);

        showProjects();

        listView = (ListView) rootView.findViewById(R.id.projectLists);
        listView.setAdapter(new MyAdapter(getActivity() ,myList));



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 //Call SentenceList Fragment
                Bundle bundle = new Bundle();
                bundle.putInt("projectId", Integer.parseInt(myList.get(position).get(3)) );

                ((Home)getActivity()).changeFragment(new SentenceListCtrl(), bundle);
            }
        });

        //Create new Profile Button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return rootView;
    }

    public static void showProjects() {
        myList.clear();
        Projects projects = new Projects();

        Cursor cursor = projects.getProjects();

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int random = (int) (Math.random() * 4 + 0);

                    // do what ever you want here
                    ArrayList<String> singleList = new ArrayList<String>();
                    singleList.add(cursor.getString(cursor.getColumnIndex("title")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("last_update_time")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("sentenceNum")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("id")));
                    singleList.add(colors[random]);


                    myList.add(singleList);

                } while (cursor.moveToNext());
            }
            cursor.close();
            emptyText.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    private void showDialog() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CreateProjectCtrl newFragment = new CreateProjectCtrl();

        newFragment.show(fragmentManager, "dialog");
        }

    @Override
    public void onResume(){
        super.onResume();
        showProjects();
    }

    class MyAdapter extends BaseAdapter {

        ArrayList<ArrayList<String>> projectListArray = new ArrayList<ArrayList<String>>();
        Context context;
        public MyAdapter(Context context, ArrayList<ArrayList<String>> arrayList) {
            this.projectListArray = arrayList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return projectListArray.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MyViewHolder holder = null;


        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.template_projectlists, parent, false);
            holder = new MyViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (MyViewHolder) v.getTag();
        }


            ArrayList<String> data = projectListArray.get(position);

            holder.titleLetter.setText(data.get(0).substring(0, 1));
            holder.titleLetter.setBackgroundColor(Color.parseColor(data.get(4)));
            holder.title.setText(data.get(0));
            holder.time.setText(data.get(1));
            holder.sentence.setText(data.get(2) + " Records");
            notifyDataSetChanged();

        return v;
        }

        class MyViewHolder {
            TextView titleLetter;
            TextView title;
            TextView time;
            TextView sentence;

           public MyViewHolder(View v) {
               titleLetter = (TextView) v.findViewById(R.id.list_titleLetter);
               title = (TextView) v.findViewById(R.id.list_title);
               time = (TextView) v.findViewById(R.id.list_time);
               sentence = (TextView) v.findViewById(R.id.list_sentence);
           }
        }
    }
}
