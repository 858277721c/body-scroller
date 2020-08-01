package com.sd.demo.body_scroller.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.demo.body_scroller.databinding.ViewInputMoreBinding;
import com.sd.lib.bodyscroller.panel.IFootPanel;
import com.sd.lib.bodyscroller.panel.ViewFootPanel;

public class InputMoreView extends FrameLayout
{
    private final ViewInputMoreBinding mBinding;
    private final IFootPanel mFootPanel = new ViewFootPanel(this);

    public InputMoreView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mBinding = ViewInputMoreBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public IFootPanel getFootPanel()
    {
        return mFootPanel;
    }
}
