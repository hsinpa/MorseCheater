package com.example.hsinpaul.morsecheater.Controllers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hsinpaul.morsecheater.EditPageFragment;
import com.example.hsinpaul.morsecheater.Home;
import com.example.hsinpaul.morsecheater.Models.Projects;
import com.example.hsinpaul.morsecheater.R;
import com.example.hsinpaul.morsecheater.Tools.MorseCodeConvertor;

import java.util.ArrayList;

/**
 * Created by hsinpaul on 2014/8/26.
 */
public class SentenceListCtrl extends Fragment {
    public static ListView listView;
    public static ArrayList<ArrayList<String>> myList = new ArrayList<ArrayList<String>>();
    public static String[] colors;
    public static ImageView emptyText;
    int projectId;
    public SentenceListCtrl() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.projectlist, container, false);
        colors = getResources().getStringArray(R.array.letterColor);
        Button button= (Button) rootView.findViewById(R.id.createButton);
        emptyText = (ImageView) rootView.findViewById(R.id.emptyData);

        Bundle args = getArguments();
        projectId = args.getInt("projectId", 0);

        //Call database
        showProjects(projectId);

        listView = (ListView) rootView.findViewById(R.id.projectLists);
        listView.setAdapter(new MyAdapter(getActivity() ,myList));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("projectId", projectId );
                ((Home)getActivity()).changeFragment(new CreateSentenceCtrl(), bundle);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), EditPageFragment.class);
                i.putExtra("projectId", projectId);
                i.putExtra("sentencePos", position);

                startActivity(i);
            }
        });

        return rootView;
    }

    public static void showProjects(int projectId) {
        myList.clear();
        Projects projects = new Projects();

        Cursor cursor = projects.getProjectDetails(projectId);

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    int random = (int) (Math.random() * 4 + 0);

                    // do what ever you want here
                    ArrayList<String> singleList = new ArrayList<String>();
                    singleList.add(cursor.getString(cursor.getColumnIndex("id")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("last_update_time")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("sentence")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("morsecode")));
                    singleList.add(cursor.getString(cursor.getColumnIndex("previewCode")));

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
                v = inflater.inflate(R.layout.template_sentencelists, parent, false);
                holder = new MyViewHolder(v);
                v.setTag(holder);
            } else {
                holder = (MyViewHolder) v.getTag();
            }

            final ArrayList<String> data = projectListArray.get(position);
            //0=id, 1 = last_update_time, 2=sentence, 3=morsecode, 4 = preview code, 5 = color
            holder.sentence.setText(data.get(2));
            holder.time.setText(data.get(1));
            holder.playHolder.setBackgroundColor(Color.parseColor(data.get(5)));

            holder.playButton.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 Log.d("vibrate",data.get(3) );
                 Log.d("vibrate",data.get(4) );

                 MorseCodeConvertor.vibrate(getActivity(), data.get(3));
                 }
             });
                    notifyDataSetChanged();

            return v;
        }
        class MyViewHolder {
            TextView time;
            TextView sentence;
            Button playButton;
            RelativeLayout playHolder;

            public MyViewHolder(View v) {
                sentence = (TextView) v.findViewById(R.id.sentence);
                time = (TextView) v.findViewById(R.id.list_time);
                playButton = (Button) v.findViewById(R.id.sentence_play);
                playHolder = (RelativeLayout) v.findViewById(R.id.sentence_playHolder);
            }
        }
    }


}
