package com.sd.demo.body_scroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.body_scroller.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnKeyboard.setOnClickListener(this);
        mBinding.btnBodyScroller.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mBinding.btnKeyboard)
        {
            startActivity(new Intent(this, KeyboardActivity.class));
        } else if (v == mBinding.btnBodyScroller)
        {
            startActivity(new Intent(this, BodyScrollerActivity.class));
        }
    }
}