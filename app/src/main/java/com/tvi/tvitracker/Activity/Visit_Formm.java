package com.tvi.tvitracker.Activity;

import android.animation.Animator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tvi.tvitracker.R;
import com.tvi.tvitracker.Utils.Circular_Reveal_Animation;
import com.tvi.tvitracker.databinding.ActivityVisitFormmBinding;

import java.util.ArrayList;
import java.util.List;

public class Visit_Formm extends AppCompatActivity {
    ActivityVisitFormmBinding binding;

Circular_Reveal_Animation circular_reveal_animation;
    List<String> statelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_visit__formm);
        setSupportActionBar(binding.toolbar);
        circular_reveal_animation=new Circular_Reveal_Animation();
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

        if (savedInstanceState == null) {
            binding.LinearL.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = binding.LinearL.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onGlobalLayout() {
                        circular_reveal_animation.circularRevealActivity(binding.LinearL,Visit_Formm.this);
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            binding.LinearL.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            binding.LinearL.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
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
    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = binding.LinearL.getRight()-circular_reveal_animation.getDips(48,Visit_Formm.this);
            int cy =binding.LinearL.getBottom()-circular_reveal_animation.getDips(48,Visit_Formm.this);
            float finalRadius = Math.max(binding.LinearL.getWidth(), binding.LinearL.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(binding.LinearL, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    binding.LinearL.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(500);
            circularReveal.start();
        }else{
            super.onBackPressed();
        }
    }

}





