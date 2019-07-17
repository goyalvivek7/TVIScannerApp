package com.tvi.tvitracker.Activity;

import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tvi.tvitracker.R;
import com.tvi.tvitracker.Utils.Logger1;
import com.tvi.tvitracker.databinding.ActivityAddLeadBinding;


public class AddLeadActivity extends AppCompatActivity {
    ActivityAddLeadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_lead);
        if (getIntent() != null) {
            if (getIntent().getStringExtra("mobileno") != null) {
                String mobileno = getIntent().getStringExtra("mobileno");
                binding.mobileno.setText(getIntent().getStringExtra("mobileno"));
                Logger1.e("second mobileno",getIntent().getStringExtra("mobileno"));
            }
            if (getIntent().getStringExtra("name") != null) {
                binding.names.setText(getIntent().getStringExtra("name"));
                Logger1.e("second name",getIntent().getStringExtra("name"));
            }
        }
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Add Lead");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
