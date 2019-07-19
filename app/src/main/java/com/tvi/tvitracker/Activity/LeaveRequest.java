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

import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityLeaveRequestBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LeaveRequest extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ActivityLeaveRequestBinding binding;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_leave_request);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Leave Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(
                LeaveRequest.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK, LeaveRequest.this, year, month, day);

        binding.fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

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

    public boolean checkValidation(){
        if (binding.reason.getText().toString().isEmpty()){
            binding.reason.setError("Can't remain blank");
            binding.reason.requestFocus();
            return false;
        }else if (binding.leavetype.getSelectedItemPosition() == 0){
            Toast.makeText(this, "Please Select Leave Type", Toast.LENGTH_SHORT).show();
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
        binding.fromday.setText(dateFormatter.format(newDate.getTime()));
        binding.frommnth.setText(dateFormatter1.format(newDate.getTime()));
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
