package com.sd.demo.body_scroller;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.body_scroller.databinding.ActivityBodyScrollerBinding;
import com.sd.lib.bodyscroller.FBodyScroller;
import com.sd.lib.bodyscroller.panel.KeyboardFootPanel;

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

        mBodyScroller.addFootPanel(new KeyboardFootPanel(this));
    }

    private final FBodyScroller mBodyScroller = new FBodyScroller()
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
}