package com.sd.lib.bodyscroller.panel;

import android.app.Activity;

import com.sd.lib.bodyscroller.ext.FKeyboardListener;

public class KeyboardFootPanel implements IFootPanel
{
    private final FKeyboardListener mKeyboardListener;
    private IFootPanel.HeightChangeCallback mCallback;
    private int mHeight;

    public KeyboardFootPanel(Activity activity)
    {
        mKeyboardListener = FKeyboardListener.of(activity);
    }

    private final FKeyboardListener.Callback mKeyboardCallback = new FKeyboardListener.Callback()
    {
        @Override
        public void onKeyboardHeightChanged(int oldHeight, int newHeight)
        {
            KeyboardFootPanel.this.setHeight(newHeight);
        }
    };

    private void setHeight(int height)
    {
        if (mHeight != height)
        {
            mHeight = height;

            if (mCallback != null)
                mCallback.onHeightChanged(height);
        }
    }

    @Override
    public int getPanelHeight()
    {
        return mHeight;
    }

    @Override
    public void initPanel(HeightChangeCallback callback)
    {
        mCallback = callback;
        mKeyboardListener.addCallback(mKeyboardCallback);
    }

    @Override
    public void releasePanel()
    {
        mCallback = null;
        mKeyboardListener.removeCallback(mKeyboardCallback);
    }
}
