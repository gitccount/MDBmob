package com.example.drawerlayout_fragment.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.drawerlayout_fragment.Adapter.FocusListViewAdapter;
import com.example.drawerlayout_fragment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class NewsFragment extends Fragment {
    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_news, null);
        view = inflater.inflate(R.layout.focus, null);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ListView listView = (ListView)getApplicationContext().findViewById(R.id.focus_listView);
        ListView listView = (ListView)view.findViewById(R.id.focus_listView);
        List<String> data = new ArrayList<String>();
        data.add("I am item 1");
        data.add("I am item 2");
        data.add("I am item 3");
        data.add("I am item 4");
        data.add("I am item 5");
        final FocusListViewAdapter adapter = new FocusListViewAdapter(this.getActivity(), data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adapter.setCurrentItem(position);
                adapter.notifyDataSetChanged();
            }
        });

    }
    public void onAttach(Activity activity) {

        super.onAttach(activity);

    }
}
