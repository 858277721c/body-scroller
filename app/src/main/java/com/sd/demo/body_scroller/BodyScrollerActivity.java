package com.sd.demo.body_scroller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.body_scroller.databinding.ActivityBodyScrollerBinding;

public class BodyScrollerActivity extends AppCompatActivity
{
    private static final String TAG = BodyScrollerActivity.class.getSimpleName();
    private ActivityBodyScrollerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBodyScrollerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }
}