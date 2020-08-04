package com.sd.demo.body_scroller;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.body_scroller.databinding.ActivityKeyboardBinding;
import com.sd.lib.bodyscroller.ext.FKeyboardListener;

/**
 * 软键盘监听
 */
public class KeyboardActivity extends AppCompatActivity
{
    private static final String TAG = KeyboardActivity.class.getSimpleName();
    private ActivityKeyboardBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityKeyboardBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        FKeyboardListener.of(this).addCallback(new FKeyboardListener.Callback()
        {
            @Override
            public void onKeyboardHeightChanged(int height, FKeyboardListener listener)
            {
                Log.i(TAG, "FKeyboardListener onKeyboardHeightChanged height:" + height + " visibleHeight:" + listener.getKeyboardVisibleHeight());
            }
        });
    }
}