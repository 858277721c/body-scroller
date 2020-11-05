package com.sd.lib.bodyscroller.panel;

import android.app.Activity;

import com.sd.lib.bodyscroller.ext.FKeyboardListener;

import java.lang.ref.WeakReference;

/**
 * 软键盘面板
 */
public class KeyboardFootPanel extends BaseFootPanel
{
    private final WeakReference<Activity> mActivity;

    public KeyboardFootPanel(Activity activity)
    {
        mActivity = new WeakReference<>(activity);
    }

    private FKeyboardListener getKeyboardListener()
    {
        final Activity activity = mActivity.get();
        return FKeyboardListener.of(activity);
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
            if (callback == null)
            {
                releasePanel();
                return;
            }

            callback.onHeightChanged(height);
        }
    };

    @Override
    public int getPanelHeight()
    {
        final FKeyboardListener listener = getKeyboardListener();
        return listener == null ? 0 : listener.getKeyboardHeight();
    }

    @Override
    public void initPanel(HeightChangeCallback callback)
    {
        super.initPanel(callback);
        final FKeyboardListener listener = getKeyboardListener();
        if (listener != null)
            listener.addCallback(mKeyboardCallback);
    }

    @Override
    public void releasePanel()
    {
        super.releasePanel();
        final FKeyboardListener listener = getKeyboardListener();
        if (listener != null)
            listener.removeCallback(mKeyboardCallback);
    }
}
