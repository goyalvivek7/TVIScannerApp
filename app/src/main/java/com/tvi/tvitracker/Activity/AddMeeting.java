package com.tvi.tvitracker.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityAddMeetingBinding;
import com.tvi.tvitracker.databinding.ActivityLeaveRequestBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddMeeting extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    ActivityAddMeetingBinding binding;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_meeting);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Add Meeting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(
                AddMeeting.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK, AddMeeting.this, year, month, day);


        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()){
                    onBackPressed();
                }
            }
        });
    }

    @Override
    protected void setUp() {

    }

    public boolean checkValidation(){
        if (binding.reason.getText().toString().isEmpty()){
            binding.reason.setError("Can't remain blank");
            binding.reason.requestFocus();
            return false;
        }else
            return true;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(i, i1, i2);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("MMM yyyy");

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
