package com.tvi.tvitracker.Fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvi.tvitracker.ModelClasses.DashboardItem;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.Adapter.DashboardAdapter;
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
        DashboardItem item = new DashboardItem("1","Punch In",R.drawable.ic_client);
        DashboardItem item1 = new DashboardItem("2","Leave Request",R.drawable.ic_calender);
        DashboardItem item2 = new DashboardItem("3","Visit",R.drawable.ic_client);
        DashboardItem item3 = new DashboardItem("4","Expenses",R.drawable.ic_wallet);
        DashboardItem item4 = new DashboardItem("5","Meeting",R.drawable.ic_meeting);
        DashboardItem item5 = new DashboardItem("6","New Client Registration",R.drawable.ic_client);
        DashboardItem item6 = new DashboardItem("7","Notification",R.drawable.ic_notification);
        DashboardItem item7 = new DashboardItem("8","Report",R.drawable.ic_report);
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
