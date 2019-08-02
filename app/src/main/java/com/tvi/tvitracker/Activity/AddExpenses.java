package com.tvi.tvitracker.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.tvi.tvitracker.Adapter.ImageAdapter;
import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.Interface.Delete_Image;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityAddExpenseBinding;
import com.tvi.tvitracker.databinding.ActivityAddMeetingBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddExpenses extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    ActivityAddExpenseBinding binding;
    DatePickerDialog datePickerDialog;
    String type = "";
    List<String> expenseheadlist = new ArrayList<>();
    List<String> paymentmodelist = new ArrayList<>();
    List<File> selectedfiles = new ArrayList<>();
    ImageAdapter adapter;

    Delete_Image callback = new Delete_Image() {
        @Override
        public void delete(int position) {
            selectedfiles.remove(position);
            adapter.additems(selectedfiles);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_expense);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Add Expense");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(0xFFFFFFFF);
        String[] expensearray = getResources().getStringArray(R.array.expensehead);
        String[] paymentarray = getResources().getStringArray(R.array.paymenttype);
        expenseheadlist = Arrays.asList(expensearray);
        paymentmodelist = Arrays.asList(paymentarray);

        adapter = new ImageAdapter(this, callback);
        binding.bills.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        binding.bills.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(
                AddExpenses.this,R.style.datepicker, AddExpenses.this, year, month, day);

        CustomAdapter adapter1 = new CustomAdapter(this, R.layout.custom_spinner_items, R.id.textView, expenseheadlist);
        binding.selecthead.setAdapter(adapter1);

        CustomAdapter adapter2 = new CustomAdapter(this, R.layout.custom_spinner_items, R.id.textView, paymentmodelist);
        binding.typetype.setAdapter(adapter2);

        binding.fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        binding.addbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddExpenses.this);
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

        binding.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.approval){
                    type = "approval";
                    binding.amountCard.setVisibility(View.VISIBLE);
                    binding.amount.setHint("Expected Amount");
                    binding.expenseheadCard.setVisibility(View.VISIBLE);
                    binding.expensenameCard.setVisibility(View.VISIBLE);
                    binding.billCard.setVisibility(View.GONE);
                    binding.paymentCard.setVisibility(View.GONE);
                }else if (checkedId == R.id.expenses){
                    type = "expense";
                    binding.amountCard.setVisibility(View.VISIBLE);
                    binding.amount.setHint("Enter Amount");
                    binding.expenseheadCard.setVisibility(View.VISIBLE);
                    binding.expensenameCard.setVisibility(View.VISIBLE);
                    binding.billCard.setVisibility(View.VISIBLE);
                    binding.paymentCard.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void setUp() {

    }

    public boolean checkValidation(){
        if (type.isEmpty()){
            Toast.makeText(this, "Please Select Expense type", Toast.LENGTH_SHORT).show();
            return false;
        }else if (type.equalsIgnoreCase("approval")){
            return validationforapproval();
        }else if (type.equalsIgnoreCase("expense")){
            return validationforexpense();
        }else
            return true;
    }

    public boolean validationforapproval(){
        if (binding.selecthead.getSelectedItemPosition()==0){
            Toast.makeText(this, "Please Select Expense head", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.expensename.getText().toString().isEmpty()){
            binding.expensename.setError("Please enter Expense name");
            binding.expensename.requestFocus();
            return false;
        }else
            return true;
    }

    public boolean validationforexpense(){

        if (binding.selecthead.getSelectedItemPosition()==0){
            Toast.makeText(this, "Please Select Expense head", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.expensename.getText().toString().isEmpty()){
            binding.expensename.setError("Please enter Expense name");
            binding.expensename.requestFocus();
            return false;
        }else if (binding.amount.getText().toString().isEmpty()){
            binding.expensename.setError("Please enter Expense amount");
            binding.expensename.requestFocus();
            return false;
        }else if (binding.bill.getDrawable() == null){
            Toast.makeText(this, "Please Upload Bill", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.typetype.getSelectedItemPosition()==0){
            Toast.makeText(this, "Please Select Payment Mode", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File file = new File(resultUri.getPath());
                Log.e("file", file.getPath());
                selectedfiles.add(file);
                adapter.additem(file);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("Error", error.getMessage());
            }
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
