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

        mKeyboardListener.start();
    }

    /**
     * 软键盘监听
     */
    private final FKeyboardListener mKeyboardListener = new FKeyboardListener(this)
    {
        @Override
        protected void onWindowHeightChanged(int oldHeight, int newHeight)
        {
            Log.i(TAG, "FKeyboardListener onWindowHeightChanged oldHeight:" + oldHeight + " newHeight:" + newHeight);
        }

        @Override
        protected void onKeyboardHeightChanged(int oldHeight, int newHeight)
        {
            Log.i(TAG, "FKeyboardListener onKeyboardHeightChanged oldHeight:" + oldHeight + " newHeight:" + newHeight);
        }
    };
}