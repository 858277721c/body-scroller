package com.sd.lib.bodyscroller;

import android.view.View;

public class ViewFootPanel implements IFootPanel
{
    private final View mView;
    private HeightChangeCallback mCallback;

    public ViewFootPanel(View view)
    {
        mView = view;
        mView.addOnLayoutChangeListener(mOnLayoutChangeListener);
    }

    private final View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener()
    {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
        {
            final int height = bottom - top;
            if (mCallback != null)
                mCallback.onHeightChanged(height);
        }
    };

    @Override
    public int getPanelHeight()
    {
        return mView.getHeight();
    }

    @Override
    public void setHeightChangeCallback(HeightChangeCallback callback)
    {
        mCallback = callback;
    }
}
