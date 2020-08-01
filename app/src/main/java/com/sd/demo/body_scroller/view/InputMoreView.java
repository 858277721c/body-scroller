package com.sd.demo.body_scroller.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.demo.body_scroller.databinding.ViewInputMoreBinding;

public class InputMoreView extends FrameLayout
{
    private final ViewInputMoreBinding mBinding;

    public InputMoreView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mBinding = ViewInputMoreBinding.inflate(LayoutInflater.from(context), this, true);
    }
}
