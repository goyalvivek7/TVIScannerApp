package com.tvi.tvitracker.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvi.tvitracker.Model.LeaveModel;
import com.tvi.tvitracker.R;

import java.util.List;

public class LeavesAdapter extends RecyclerView.Adapter<LeavesAdapter.Viewholder> {

    Context context;
    List<LeaveModel> modelList;

    public LeavesAdapter(Context context, List<LeaveModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.leaveitem,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
