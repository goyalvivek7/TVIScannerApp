package com.tvi.tvitracker.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvi.tvitracker.R;

public class Visit_adapter extends RecyclerView.Adapter<Visit_adapter.Visit> {
    Context context;

    public Visit_adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Visit onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.visit_item,viewGroup,false);
        return new Visit_adapter.Visit(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Visit visit, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Visit extends RecyclerView.ViewHolder {

        public Visit(@NonNull View itemView) {
            super(itemView);
        }
    }
}
