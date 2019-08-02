package com.tvi.tvitracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvi.tvitracker.Activity.AddLeadActivity;
import com.tvi.tvitracker.Activity.AllExpenses;
import com.tvi.tvitracker.Activity.AllLeaves;
import com.tvi.tvitracker.Activity.AllMeetings;
import com.tvi.tvitracker.Activity.PuntchInActivity;
import com.tvi.tvitracker.Activity.Visit_list;
import com.tvi.tvitracker.ModelClasses.DashboardItem;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.DashboardRecyclerItemBinding;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.Viewholder> {

    Context context;
    List<DashboardItem> modelList;

    public DashboardAdapter(Context context, List<DashboardItem> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        DashboardRecyclerItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dashboard_recycler_item, viewGroup, false
        );
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, final int i) {
        viewholder.binding.setModel(modelList.get(i));
        viewholder.binding.logo.setImageDrawable(context.getResources().getDrawable(modelList.get(i).getImage()));

        viewholder.binding.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 0:
                        context.startActivity(new Intent(context, PuntchInActivity.class));

                        break;
                    case 1:
                        context.startActivity(new Intent(context, AllLeaves.class));

                        break;
                    case 2:
                        context.startActivity(new Intent(context, Visit_list.class));

                        break;
                    case 3:
                        context.startActivity(new Intent(context, AllExpenses.class));

                        break;
                    case 4:
                        context.startActivity(new Intent(context, AllMeetings.class));

                        break;
                    case 5:
                        context.startActivity(new Intent(context, AddLeadActivity.class));

                        break;
                    case 6:
                        context.startActivity(new Intent(context, AllLeaves.class));

                        break;
                    case 7:
                        context.startActivity(new Intent(context, AllLeaves.class));

                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        DashboardRecyclerItemBinding binding;

        public Viewholder(@NonNull DashboardRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
