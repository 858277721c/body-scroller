package com.sd.lib.bodyscroller.ext;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 键盘监听
 */
public class FKeyboardListener
{
    private final Activity mActivity;

    private InternalPopupWindow mPopupWindow;
    private final Rect mRect = new Rect();

    private int mWindowHeight;
    private int mMaxWindowHeight;
    private int mKeyboardHeight;

    private final Map<Callback, String> mCallbacks = new ConcurrentHashMap<>();

    private FKeyboardListener(Activity activity)
    {
        mActivity = activity;
    }

    /**
     * 添加回调
     *
     * @param callback
     */
    public void addCallback(Callback callback)
    {
        if (callback != null)
            mCallbacks.put(callback, "");
    }

    /**
     * 移除回调
     *
     * @param callback
     */
    public void removeCallback(Callback callback)
    {
        if (callback != null)
            mCallbacks.remove(callback);
    }

    private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener()
    {
        @Override
        public void onGlobalLayout()
        {
            if (mPopupWindow == null)
                return;

            mPopupWindow.mView.getWindowVisibleDisplayFrame(mRect);

            final int old = mWindowHeight;
            final int height = mRect.height();

            if (old != height)
            {
                mWindowHeight = height;
                onWindowHeightChanged(old, height);

                if (height > mMaxWindowHeight)
                    mMaxWindowHeight = height;

                final int oldKeyboardHeight = mKeyboardHeight;
                final int keyboardHeight = mMaxWindowHeight - height;
                if (oldKeyboardHeight != keyboardHeight)
                {
                    mKeyboardHeight = keyboardHeight;
                    onKeyboardHeightChanged(oldKeyboardHeight, keyboardHeight);
                }
            }
        }
    };

    private View getTarget()
    {
        return mActivity.findViewById(Window.ID_ANDROID_CONTENT);
    }

    /**
     * 开始监听
     */
    public final void start()
    {
        if (mActivity.isFinishing())
            return;

        final Application application = mActivity.getApplication();
        application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);

        getTarget().removeCallbacks(mShowRunnable);
        getTarget().post(mShowRunnable);
    }

    /**
     * 停止监听
     */
    public final void stop()
    {
        final Application application = mActivity.getApplication();
        application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);

        getTarget().removeCallbacks(mShowRunnable);

        if (mPopupWindow != null)
        {
            mPopupWindow.dismiss();
            mPopupWindow = null;

            mWindowHeight = 0;
            mMaxWindowHeight = 0;
            mKeyboardHeight = 0;
        }
    }

    private final Runnable mShowRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (mPopupWindow == null)
                mPopupWindow = new InternalPopupWindow(mActivity);

            mPopupWindow.showAtLocation(getTarget(), Gravity.NO_GRAVITY, 0, 0);
        }
    };

    private void onWindowHeightChanged(int oldHeight, int newHeight)
    {
    }

    /**
     * 键盘高度变化
     *
     * @param oldHeight
     * @param newHeight
     */
    private void onKeyboardHeightChanged(int oldHeight, int newHeight)
    {
        for (Callback item : mCallbacks.keySet())
        {
            item.onKeyboardHeightChanged(oldHeight, newHeight);
        }
    }

    private final class InternalPopupWindow extends PopupWindow implements View.OnAttachStateChangeListener
    {
        private final View mView;

        public InternalPopupWindow(Context context)
        {
            mView = new View(context);
            mView.addOnAttachStateChangeListener(this);

            setContentView(mView);
            setWidth(1);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setInputMethodMode(INPUT_METHOD_NEEDED);
        }

        @Override
        public void onViewAttachedToWindow(View v)
        {
            v.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }

        @Override
        public void onViewDetachedFromWindow(View v)
        {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }

    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks()
    {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState)
        {
        }

        @Override
        public void onActivityStarted(Activity activity)
        {
        }

        @Override
        public void onActivityResumed(Activity activity)
        {
        }

        @Override
        public void onActivityPaused(Activity activity)
        {
        }

        @Override
        public void onActivityStopped(Activity activity)
        {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState)
        {
        }

        @Override
        public void onActivityDestroyed(Activity activity)
        {
            if (mActivity == activity)
                removeActivity(activity);
        }
    };

    public interface Callback
    {
        /**
         * 键盘高度变化回调
         *
         * @param oldHeight
         * @param newHeight
         */
        void onKeyboardHeightChanged(int oldHeight, int newHeight);
    }

    //---------- static ----------

    private static final Map<Activity, FKeyboardListener> MAP_LISTENER = new WeakHashMap<>();

    public static synchronized FKeyboardListener of(Activity activity)
    {
        FKeyboardListener listener = MAP_LISTENER.get(activity);
        if (listener == null)
        {
            listener = new FKeyboardListener(activity);
            if (!activity.isFinishing())
            {
                MAP_LISTENER.put(activity, listener);
                listener.start();
            }
        }
        return listener;
    }

    private static synchronized void removeActivity(Activity activity)
    {
        final FKeyboardListener listener = MAP_LISTENER.remove(activity);
        if (listener != null)
            listener.stop();
    }
}
