package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bo.cloudmusic.databinding.ActivityLoginOrRegisterBinding;

public class LoginOrRegisterActivity extends AppCompatActivity {
    private ActivityLoginOrRegisterBinding activityLoginOrRegisterBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginOrRegisterBinding=ActivityLoginOrRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityLoginOrRegisterBinding.getRoot());
    }
}