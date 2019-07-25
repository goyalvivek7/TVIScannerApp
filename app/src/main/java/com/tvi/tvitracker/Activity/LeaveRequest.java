package com.tvi.tvitracker.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.Utils.Logger1;
import com.tvi.tvitracker.Utils.StaticDataHelper;
import com.tvi.tvitracker.databinding.ActivityLeaveRequestBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LeaveRequest extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    ActivityLeaveRequestBinding binding;
    DatePickerDialog datePickerDialog;
    String datefor = "";

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

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("MMM yyyy");
            binding.fromday.setText(dateFormatter.format(c.getTime()));
            binding.frommnth.setText(dateFormatter1.format(c.getTime()));
            fromdate = c;
            binding.today.setText(dateFormatter.format(c.getTime()));
            binding.tomnth.setText(dateFormatter1.format(c.getTime()));
            todate = c;


        binding.samedate.setVisibility(View.VISIBLE);

        datePickerDialog = new DatePickerDialog(
                LeaveRequest.this,R.style.datepicker, LeaveRequest.this, year, month, day);

        binding.fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datefor = "from";
                datePickerDialog.show();
            }
        });

        binding.todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datefor = "to";
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

        String[] paymentarray = getResources().getStringArray(R.array.leavetype);
        List<String> expenseheadlist = Arrays.asList(paymentarray);

        CustomAdapter adapter1 = new CustomAdapter(this, R.layout.custom_spinner_items, R.id.textView, expenseheadlist);
        binding.leavetype.setAdapter(adapter1);

    }

    @Override
    protected void setUp() {

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
    Calendar fromdate, todate;
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(i, i1, i2);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("MMM yyyy");
        if (datefor.equalsIgnoreCase("from")) {
            binding.fromday.setText(dateFormatter.format(newDate.getTime()));
            binding.frommnth.setText(dateFormatter1.format(newDate.getTime()));
            fromdate = newDate;
        }else {
            binding.today.setText(dateFormatter.format(newDate.getTime()));
            binding.tomnth.setText(dateFormatter1.format(newDate.getTime()));
            todate = newDate;
        }

        if (StaticDataHelper.isSameDay(fromdate,todate)){
            binding.samedate.setVisibility(View.VISIBLE);
        }else {
            binding.samedate.setVisibility(View.GONE);
        }

        Logger1.e("days count","" + (StaticDataHelper.gettimedifference1(fromdate,todate)+1));
        binding.totaldays.setText("" + (StaticDataHelper.gettimedifference1(fromdate,todate)+1));
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
