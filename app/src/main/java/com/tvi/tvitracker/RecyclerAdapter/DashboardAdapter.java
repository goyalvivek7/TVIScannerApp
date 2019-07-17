package com.tvi.tvitracker.RecyclerAdapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
                LayoutInflater.from(context), R.layout.dashboard_recycler_item,viewGroup,false
        );

        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
            viewholder.binding.setModel(modelList.get(i));
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
