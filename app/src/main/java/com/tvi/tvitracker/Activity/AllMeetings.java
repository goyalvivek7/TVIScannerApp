package com.tvi.tvitracker.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.tvi.tvitracker.Adapter.LeavesAdapter;
import com.tvi.tvitracker.Adapter.MeetingAdapter;
import com.tvi.tvitracker.Model.LeaveModel;
import com.tvi.tvitracker.Model.MeetingModel;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityAllLeavesBinding;

import java.util.ArrayList;
import java.util.List;

public class AllMeetings extends AppCompatActivity {

    ActivityAllLeavesBinding binding;
    List<MeetingModel> modellist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_all_leaves);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("All Meetings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);
        modellist = new ArrayList<>();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(new MeetingAdapter(this,modellist));

        binding.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllMeetings.this,AddMeeting.class));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateInAndOut(AllMeetings.this);
    }
}
