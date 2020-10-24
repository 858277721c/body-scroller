package com.sd.lib.bodyscroller.ext;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class FKeyboardHeightKeeper
{
    private final Activity mActivity;
    private final Map<View, ViewConfig> mViewHolder = new WeakHashMap<>();

    private FKeyboardListener mKeyboardListener;
    private int mKeyboardHeight;

    public FKeyboardHeightKeeper(Activity activity)
    {
        if (activity == null)
            throw new NullPointerException("activity is null");
        mActivity = activity;
    }

    private FKeyboardListener getKeyboardListener()
    {
        if (mKeyboardListener == null)
        {
            mKeyboardListener = FKeyboardListener.of(mActivity);
            mKeyboardListener.addCallback(mCallback);
        }
        return mKeyboardListener;
    }

    /**
     * 添加View
     *
     * @param view
     */
    public void addView(View view)
    {
        if (view == null)
            return;

        if (mViewHolder.containsKey(view))
            return;

        final ViewConfig config = new ViewConfig(view);
        mViewHolder.put(view, config);

        int height = getKeyboardListener().getKeyboardVisibleHeight();
        if (height <= 0)
            height = FKeyboardListener.getCachedKeyboardVisibleHeight();

        height = fixHeight(height);
        config.updateHeight(height);
    }

    /**
     * 移除View
     *
     * @param view
     */
    public void removeView(View view)
    {
        if (view == null)
            return;

        mViewHolder.remove(view);
    }

    private void notifyHeight(int height)
    {
        if (height == 0)
            return;

        height = fixHeight(height);
        if (mKeyboardHeight != height)
        {
            mKeyboardHeight = height;
            for (ViewConfig item : mViewHolder.values())
            {
                item.updateHeight(height);
            }
        }
    }

    private int fixHeight(int height)
    {
        return height;
    }

    /**
     * 监听键盘高度
     */
    private final FKeyboardListener.Callback mCallback = new FKeyboardListener.Callback()
    {
        @Override
        public void onKeyboardHeightChanged(int height, FKeyboardListener listener)
        {
            notifyHeight(height);
        }
    };

    private static final class ViewConfig
    {
        private final WeakReference<View> mView;

        private ViewConfig(View view)
        {
            if (view == null)
                throw new NullPointerException("view is null");
            mView = new WeakReference<>(view);
        }

        public void updateHeight(int height)
        {
            if (height == 0)
                return;

            final View view = mView.get();
            if (view == null)
                return;

            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params == null)
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (params.height != height)
            {
                params.height = height;
                view.setLayoutParams(params);
            }
        }
    }
}
