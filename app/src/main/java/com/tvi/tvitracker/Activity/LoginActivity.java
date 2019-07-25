package com.tvi.tvitracker.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tvi.tvitracker.BASE.BaseActivity;
import com.tvi.tvitracker.R;
import com.tvi.tvitracker.databinding.ActivityLoginBinding;


public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;
    boolean loginwithotps = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        });

        binding.loginwithotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginwithotps) {
                    binding.passlay.setVisibility(View.GONE);
                    binding.email.setHint("Mobile No.");
                    binding.loginwithotp.setText("Login With Password");
                    binding.login.setText("Send OTP");
                    loginwithotps = true;
                }else{
                    binding.passlay.setVisibility(View.VISIBLE);
                    binding.email.setHint("Email id / Mobile No.");
                    binding.loginwithotp.setText("Login With OTP");
                    binding.login.setText("Login");
                    loginwithotps = false;
                }
            }
        });
    }

    @Override
    protected void setUp() {

    }
}
