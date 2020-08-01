package com.sd.lib.bodyscroller.ext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 键盘监听
 */
public abstract class FKeyboardListener extends PopupWindow
{
    private final Activity mActivity;
    private InternalPopupWindow mPopupWindow;
    private final Rect mRect = new Rect();

    private int mWindowHeight;
    private int mMaxWindowHeight;
    private int mKeyboardHeight;

    public FKeyboardListener(Activity activity)
    {
        mActivity = activity;
    }

    private InternalPopupWindow getPopupWindow()
    {
        if (mPopupWindow == null)
            mPopupWindow = new InternalPopupWindow(mActivity);
        return mPopupWindow;
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

    /**
     * 开始监听
     */
    public final void start()
    {
        final View target = mActivity.findViewById(Window.ID_ANDROID_CONTENT);
        getPopupWindow().showAtLocation(target, Gravity.NO_GRAVITY, 0, 0);
    }

    /**
     * 停止监听
     */
    public final void stop()
    {
        if (mPopupWindow != null)
        {
            mPopupWindow.dismiss();
            mPopupWindow = null;

            mWindowHeight = 0;
            mMaxWindowHeight = 0;
            mKeyboardHeight = 0;
        }
    }

    protected void onWindowHeightChanged(int oldHeight, int newHeight)
    {
    }

    /**
     * 键盘高度变化
     *
     * @param oldHeight
     * @param newHeight
     */
    protected abstract void onKeyboardHeightChanged(int oldHeight, int newHeight);

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
}
