package com.tvi.tvitracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tvi.tvitracker.Interface.Delete_Image;
import com.tvi.tvitracker.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Viewholder> {

    List<File> listofimages;
    Context context;
    Delete_Image callback;

    public ImageAdapter(Context context, Delete_Image callback) {
        this.context = context;
        this.callback = callback;
        listofimages = new ArrayList<>();
    }

    public void additem(File file) {
        listofimages.add(file);
        Log.e("list",""+listofimages.size());
        notifyDataSetChanged();
    }

    public void additems(List<File> file) {
        listofimages.clear();
        listofimages.addAll(file);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_adapter, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, final int i) {
        Picasso.get().load(listofimages.get(i)).into(viewholder.imageView);

        viewholder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.delete(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listofimages.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView close;
        CardView mainlayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.prescription);
            close = itemView.findViewById(R.id.close);
            mainlayout = itemView.findViewById(R.id.mainlayout);
        }
    }
}

