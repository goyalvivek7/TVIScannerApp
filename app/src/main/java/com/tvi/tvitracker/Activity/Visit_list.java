package com.tvi.tvitracker.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tvi.tvitracker.Adapter.LeavesAdapter;
import com.tvi.tvitracker.Adapter.Visit_adapter;
import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityVisitListBinding;

public class Visit_list extends BaseActivity {
    ActivityVisitListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      binding= DataBindingUtil.setContentView(this,R.layout.activity_visit_list);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Visit List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(new Visit_adapter(this));
        binding.visit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(Visit_list.this,Visit_Formm.class));
          }
      });
    }

    @Override
    protected void setUp() {

    }
}
