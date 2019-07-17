package com.tvi.tvitracker.Fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvi.tvitracker.ModelClasses.DashboardItem;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.RecyclerAdapter.DashboardAdapter;
import com.tvi.tvitracker.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    List<DashboardItem> modelList = new ArrayList<>();
    FragmentHomeBinding binding;
    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        Preparelist();
        return binding.getRoot();
    }

    public void Preparelist(){
        DashboardItem item = new DashboardItem("1","Punch In","image");
        DashboardItem item1 = new DashboardItem("2","Leave Request","image");
        DashboardItem item2 = new DashboardItem("3","Visit","image");
        DashboardItem item3 = new DashboardItem("4","Expences","image");
        DashboardItem item4 = new DashboardItem("5","Meeting","image");
        DashboardItem item5 = new DashboardItem("6","New Client Registration","image");
        DashboardItem item6 = new DashboardItem("7","Notification","image");
        DashboardItem item7 = new DashboardItem("8","Report","image");
        modelList.add(item);
        modelList.add(item1);
        modelList.add(item2);
        modelList.add(item3);
        modelList.add(item4);
        modelList.add(item5);
        modelList.add(item6);
        modelList.add(item7);

        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recycler.setAdapter(new DashboardAdapter(getActivity(),modelList));

    }

}
