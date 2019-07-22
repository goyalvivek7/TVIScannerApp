package com.tvi.tvitracker.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityVisitFormmBinding;

public class Visit_Formm extends AppCompatActivity {
    ActivityVisitFormmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_visit__formm);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Add Visit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    onBackPressed();
                }
            }
        });


    }

    private boolean checkValidation() {
        if (TextUtils.isEmpty(binding.name.getText().toString())) {
            binding.name.setError("Can't remail blank");
            binding.name.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(binding.address.getText().toString())) {
            binding.address.setError("Can't remail blank");
            return false;
        } else if (TextUtils.isEmpty(binding.mobileno.getText().toString())) {
            binding.mobileno.setError("Can't remail blank");
            return false;
        } else if (TextUtils.isEmpty(binding.email.getText().toString())) {
            binding.email.setError("Can't remail blank");
            return false;
        } else if (binding.selectvisit.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Visit Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.conclusion.getText().toString())) {
            binding.conclusion.setError("Can't remail blank");
            return false;
        } else
            return true;

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





