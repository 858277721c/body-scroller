package com.sd.demo.body_scroller.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.sd.demo.body_scroller.databinding.ViewInputMoreBinding;

public class InputMoreView extends FrameLayout
{
    public static final String TAG = InputMoreView.class.getSimpleName();
    private final ViewInputMoreBinding mBinding;

    public InputMoreView(Context context)
    {
        super(context);
        mBinding = ViewInputMoreBinding.inflate(LayoutInflater.from(context), this, true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout height:" + (bottom - top));
    }
}
