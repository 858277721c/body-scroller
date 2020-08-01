package com.sd.demo.body_scroller;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.body_scroller.databinding.ActivityMainBinding;
import com.sd.lib.bodyscroller.FBodyScroller;
import com.sd.lib.bodyscroller.panel.KeyboardFootPanel;
import com.sd.lib.bodyscroller.ext.FKeyboardListener;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;
    private FKeyboardListener mKeyboardListener;

    private FBodyScroller mBodyScroller;
    private final KeyboardFootPanel mKeyboardFootPanel = new KeyboardFootPanel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.getRoot().post(new Runnable()
        {
            @Override
            public void run()
            {
                getKeyboardListener().start();
                getBodyScroller().setActiveFootPanel(mKeyboardFootPanel);
            }
        });
    }

    private FBodyScroller getBodyScroller()
    {
        if (mBodyScroller == null)
        {
            mBodyScroller = new FBodyScroller()
            {
                @Override
                protected void moveBodyUp(int delta)
                {
                    Log.i(TAG, "moveBodyUp delta:" + delta);
                    mBinding.viewRoot.scrollBy(0, delta);
                }

                @Override
                protected void moveBodyDown(int delta)
                {
                    Log.i(TAG, "moveBodyDown delta:" + delta);
                    mBinding.viewRoot.scrollBy(0, -delta);
                }
            };

            mBodyScroller.addFootPanel(mKeyboardFootPanel);
        }
        return mBodyScroller;
    }

    private FKeyboardListener getKeyboardListener()
    {
        if (mKeyboardListener == null)
        {
            mKeyboardListener = new FKeyboardListener(this)
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
        return mKeyboardListener;
    }
}