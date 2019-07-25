package com.tvi.tvitracker.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tvi.tvitracker.Adapter.LeavesAdapter;
import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.CustomDialog.Leave_Filter;
import com.tvi.tvitracker.Interface.Leave_Filter_Listener;
import com.tvi.tvitracker.Model.LeaveModel;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityAllLeavesBinding;

import java.util.ArrayList;
import java.util.List;

public class AllLeaves extends BaseActivity {

    Leave_Filter_Listener callback = new Leave_Filter_Listener() {
        @Override
        public void selectfilter(String name) {
            Toast.makeText(AllLeaves.this, name, Toast.LENGTH_SHORT).show();
        }
    };

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

        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Leave_Filter leave_filter = new Leave_Filter(AllLeaves.this,callback);
                leave_filter.show();
            }
        });

        binding.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllLeaves.this,LeaveRequest.class));
            }
        });

    }

    @Override
    protected void setUp() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
