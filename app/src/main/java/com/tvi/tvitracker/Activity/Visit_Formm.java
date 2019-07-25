package com.tvi.tvitracker.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityVisitFormmBinding;

import java.util.ArrayList;
import java.util.List;

public class Visit_Formm extends AppCompatActivity {
    ActivityVisitFormmBinding binding;


    List<String> statelist = new ArrayList<>();

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



        CustomAdapter adapter3 = new CustomAdapter(this, R.layout.custom_spinner_items, R.id.textView, statelist);
        binding.selectproduct.setAdapter(adapter3);


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

    public class CustomAdapter extends ArrayAdapter {

        public CustomAdapter(Context context, int resource, int textViewResourceId, List objects) {
            super(context, resource, textViewResourceId, objects);

        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView tv = view.findViewById(R.id.textView);
            if (position == 0) {
                tv.setTextColor(getResources().getColor(R.color.gray));
            } else {
                tv.setTextColor(Color.BLACK);
            }
            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (5 * scale + 0.5f);
            view.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
            TextView tv = view.findViewById(R.id.textView);
            tv.setTextSize(16);
            if (position == 0) {
                tv.setTextColor(getResources().getColor(R.color.gray));
            } else {
                tv.setTextColor(Color.BLACK);
            }
            return view;
        }
    }
}





