package com.tvi.tvitracker.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.tvi.tvitracker.Adapter.LeavesAdapter;
import com.tvi.tvitracker.Model.LeaveModel;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityAllLeavesBinding;

import java.util.ArrayList;
import java.util.List;

public class AllLeaves extends AppCompatActivity {

    ActivityAllLeavesBinding binding;
    List<LeaveModel> modellist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_all_leaves);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("All Leaves");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);
        modellist = new ArrayList<>();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(new LeavesAdapter(this,modellist));

        binding.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllLeaves.this,LeaveRequest.class));
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
