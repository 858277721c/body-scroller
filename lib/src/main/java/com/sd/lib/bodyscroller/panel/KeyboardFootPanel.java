package com.sd.lib.bodyscroller.panel;

import android.app.Activity;

import com.sd.lib.bodyscroller.ext.FKeyboardListener;

public class KeyboardFootPanel extends BaseFootPanel
{
    private final FKeyboardListener mKeyboardListener;

    public KeyboardFootPanel(Activity activity)
    {
        mKeyboardListener = FKeyboardListener.of(activity);
    }

    /**
     * 监听软键盘
     */
    private final FKeyboardListener.Callback mKeyboardCallback = new FKeyboardListener.Callback()
    {
        @Override
        public void onKeyboardHeightChanged(int height, FKeyboardListener listener)
        {
            final HeightChangeCallback callback = getHeightChangeCallback();
            if (callback != null)
            {
                callback.onHeightChanged(height);
            } else
            {
                releasePanel();
            }
        }
    };

    @Override
    public int getPanelHeight()
    {
        return mKeyboardListener.getKeyboardHeight();
    }

    @Override
    public void initPanel(HeightChangeCallback callback)
    {
        super.initPanel(callback);
        mKeyboardListener.addCallback(mKeyboardCallback);
    }

    @Override
    public void releasePanel()
    {
        super.releasePanel();
        mKeyboardListener.removeCallback(mKeyboardCallback);
    }
}
