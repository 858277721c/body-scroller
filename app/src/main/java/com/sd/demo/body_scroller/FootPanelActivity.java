package com.sd.demo.body_scroller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.body_scroller.databinding.ActivityFootPanelBinding;

public class FootPanelActivity extends AppCompatActivity
{
    private static final String TAG = FootPanelActivity.class.getSimpleName();
    private ActivityFootPanelBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFootPanelBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }
}