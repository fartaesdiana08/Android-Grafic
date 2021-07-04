package com.example.subiectgrafic;

import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineChartFragment extends Fragment {
    private boolean shouldRefreshOnResume = false;

    private static String MAP_KEY = "map";
    private Map<String, List<Exercitiu>> mapa=new HashMap<>();
    private RelativeLayout relativeLayout;

    public static LineChartFragment newInstance(HashMap<String,List<Exercitiu>> mapa) {
        LineChartFragment fragment = new LineChartFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LineChartFragment.MAP_KEY, mapa);
        fragment.setArguments(bundle);
        return fragment;
    }

    public LineChartFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_line_chart, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        relativeLayout=view.findViewById(R.id.container);
        if (getArguments() != null) {
            mapa = (Map<String, List<Exercitiu>>) getArguments().getSerializable(MAP_KEY);
        }
        relativeLayout.addView(new LineChartView(getContext().getApplicationContext(),mapa));
        if(getContext()!=null){
        }
    }

    public void notifyInternalAdapter() {
            if (getFragmentManager() != null) {
                getFragmentManager()
                        .beginTransaction()
                        .detach(this)
                        .attach(this)
                        .commit();
            }
    }

}