package com.tvi.tvitracker.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.Utils.Logger1;
import com.tvi.tvitracker.databinding.ActivityAddLeadBinding;


public class AddLeadActivity extends BaseActivity {
    ActivityAddLeadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_lead);
        if (getIntent() != null) {
            if (getIntent().getStringExtra("mobileno") != null) {
                String mobileno = getIntent().getStringExtra("mobileno");
                binding.mobile.setText(getIntent().getStringExtra("mobileno"));
                Logger1.e("second mobileno",getIntent().getStringExtra("mobileno"));
            }
            if (getIntent().getStringExtra("name") != null) {
                binding.name.setText(getIntent().getStringExtra("name"));
                Logger1.e("second name",getIntent().getStringExtra("name"));
            }
        }
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Add Client");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);
    }

    @Override
    protected void setUp() {

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateInAndOut(AddLeadActivity.this);
    }
}
