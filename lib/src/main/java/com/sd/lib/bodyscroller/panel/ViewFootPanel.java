package com.sd.lib.bodyscroller.panel;

import android.view.View;

import java.lang.ref.WeakReference;

public class ViewFootPanel extends BaseFootPanel
{
    private final WeakReference<View> mView;

    public ViewFootPanel(View view)
    {
        mView = new WeakReference<>(view);
    }

    private View getView()
    {
        return mView.get();
    }

    private final View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener()
    {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
        {
            final HeightChangeCallback callback = getHeightChangeCallback();
            if (callback != null)
            {
                if (v == getView())
                {
                    final int height = bottom - top;
                    callback.onHeightChanged(height);
                }
            } else
            {
                releasePanel();
            }
        }
    };

    @Override
    public int getPanelHeight()
    {
        final View view = getView();
        return view == null ? 0 : view.getHeight();
    }

    @Override
    public void initPanel(HeightChangeCallback callback)
    {
        super.initPanel(callback);
        final View view = getView();
        if (view != null)
        {
            view.removeOnLayoutChangeListener(mOnLayoutChangeListener);
            view.addOnLayoutChangeListener(mOnLayoutChangeListener);
        }
    }

    @Override
    public void releasePanel()
    {
        super.releasePanel();
        final View view = getView();
        if (view != null)
            view.removeOnLayoutChangeListener(mOnLayoutChangeListener);
    }
}
