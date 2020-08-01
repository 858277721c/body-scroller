package com.sd.lib.bodyscroller.panel;

import android.view.View;

import java.lang.ref.WeakReference;

public class ViewFootPanel implements IFootPanel
{
    private final WeakReference<View> mView;
    private HeightChangeCallback mCallback;

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
            if (v == getView())
            {
                final int height = bottom - top;
                if (mCallback != null)
                    mCallback.onHeightChanged(height);
            }
        }
    };

    @Override
    public int getPanelHeight()
    {
        final View view = getView();
        if (view == null)
            return 0;

        return view.getHeight();
    }

    @Override
    public void setHeightChangeCallback(HeightChangeCallback callback)
    {
        mCallback = callback;
    }

    @Override
    public void setPanelActive(boolean active)
    {
        final View view = getView();
        if (view == null)
            return;

        view.removeOnLayoutChangeListener(mOnLayoutChangeListener);
        if (active)
            view.addOnLayoutChangeListener(mOnLayoutChangeListener);
    }
}
