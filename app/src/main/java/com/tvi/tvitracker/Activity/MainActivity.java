package com.tvi.tvitracker.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.ViewpagerAdapeter.HomeAdapter;
import com.tvi.tvitracker.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        animation(binding.home,binding.setting,binding.profile);

        binding.homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewpager.setCurrentItem(1);
                animation(binding.home,binding.setting,binding.profile);
            }
        });

        binding.settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewpager.setCurrentItem(2);
                animation(binding.setting,binding.home,binding.profile);
            }
        });

        binding.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewpager.setCurrentItem(0);
                animation(binding.profile,binding.setting,binding.home);
            }
        });

        HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager());
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setCurrentItem(1);

        binding.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==0){
                    animation(binding.profile,binding.setting,binding.home);
                }else if (i==1){
                    animation(binding.home,binding.setting,binding.profile);
                }else {
                    animation(binding.setting,binding.home,binding.profile);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void setUp() {

    }

    public void animation(ImageView animated, ImageView Nonaminated, ImageView NOanimation){

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(animated, "scaleX", 1.5f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(animated, "scaleY", 1.5f);
        scaleDownX.setDuration(100);
        scaleDownY.setDuration(100);
        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);
        scaleDown.start();

        ObjectAnimator scaleDownX1 = ObjectAnimator.ofFloat(Nonaminated, "scaleX", 1.00f);
        ObjectAnimator scaleDownY1 = ObjectAnimator.ofFloat(Nonaminated, "scaleY", 1.00f);
        scaleDownX1.setDuration(100);
        scaleDownY1.setDuration(100);
        AnimatorSet scaleDown1 = new AnimatorSet();
        scaleDown1.play(scaleDownX1).with(scaleDownY1);
        scaleDown1.start();

        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(NOanimation, "scaleX", 1.00f);
        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(NOanimation, "scaleY", 1.00f);
        scaleDownX1.setDuration(100);
        scaleDownY1.setDuration(100);
        AnimatorSet scaleDown2 = new AnimatorSet();
        scaleDown2.play(scaleDownX2).with(scaleDownY2);
        scaleDown2.start();
    }

}
