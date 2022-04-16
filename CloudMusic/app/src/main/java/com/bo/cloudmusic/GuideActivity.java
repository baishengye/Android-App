package com.bo.cloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bo.cloudmusic.databinding.ActivityGuideBinding;

public class GuideActivity extends AppCompatActivity {
    private ActivityGuideBinding activityGuideBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGuideBinding=ActivityGuideBinding.inflate(getLayoutInflater());
        setContentView(activityGuideBinding.getRoot());
    }
}