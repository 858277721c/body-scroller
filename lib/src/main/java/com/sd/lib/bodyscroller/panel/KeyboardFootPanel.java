package com.sd.lib.bodyscroller.panel;

import android.app.Activity;

import com.sd.lib.bodyscroller.ext.FKeyboardListener;

public class KeyboardFootPanel implements IFootPanel
{
    private final FKeyboardListener mKeyboardListener;
    private IFootPanel.HeightChangeCallback mHeightChangeCallback;
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

            if (mHeightChangeCallback != null)
                mHeightChangeCallback.onHeightChanged(height);
        }
    }

    @Override
    public int getPanelHeight()
    {
        return mHeight;
    }

    @Override
    public void setHeightChangeCallback(IFootPanel.HeightChangeCallback callback)
    {
        mHeightChangeCallback = callback;
    }

    @Override
    public void setPanelActive(boolean active)
    {
        if (active)
            mKeyboardListener.addCallback(mKeyboardCallback);
        else
            mKeyboardListener.removeCallback(mKeyboardCallback);
    }
}
