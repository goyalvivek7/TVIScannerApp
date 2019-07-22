package com.tvi.tvitracker.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.tvi.tvitracker.Adapter.ExpenseAdapter;
import com.tvi.tvitracker.Adapter.MeetingAdapter;
import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.Model.ExpenseModel;
import com.tvi.tvitracker.Model.MeetingModel;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityAllLeavesBinding;

import java.util.ArrayList;
import java.util.List;

public class AllExpenses extends BaseActivity {

    ActivityAllLeavesBinding binding;
    List<ExpenseModel> modellist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_all_leaves);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("All Expenses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);
        modellist = new ArrayList<>();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(new ExpenseAdapter(this,modellist));

        binding.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllExpenses.this,AddExpenses.class));
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
