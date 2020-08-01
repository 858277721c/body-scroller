package com.sd.lib.bodyscroller;

import android.app.Activity;

import com.sd.lib.bodyscroller.ext.FKeyboardListener;

public class KeyboardFootPanel implements IFootPanel
{
    private final FKeyboardListener mKeyboardListener;
    private IFootPanel.HeightChangeCallback mHeightChangeCallback;
    private int mHeight;

    public KeyboardFootPanel(Activity activity)
    {
        mKeyboardListener = new FKeyboardListener(activity)
        {
            @Override
            protected void onKeyboardHeightChanged(int oldHeight, int newHeight)
            {
                KeyboardFootPanel.this.setHeight(newHeight);
            }
        };
    }

    void start()
    {
        mKeyboardListener.start();
    }

    void stop()
    {
        mKeyboardListener.stop();
    }

    private void setHeight(int height)
    {
        if (mHeight != height)
        {
            mHeight = height;

            if (mHeightChangeCallback != null)
                mHeightChangeCallback.onHeightChanged(height);
        }
    }

    @Override
    public int getHeight()
    {
        return mHeight;
    }

    @Override
    public void setHeightChangeCallback(IFootPanel.HeightChangeCallback callback)
    {
        mHeightChangeCallback = callback;
    }
}
