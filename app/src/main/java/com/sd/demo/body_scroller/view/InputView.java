package com.sd.demo.body_scroller.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.demo.body_scroller.databinding.ViewInputBinding;
import com.sd.lib.bodyscroller.FBodyScroller;
import com.sd.lib.bodyscroller.panel.IFootPanel;
import com.sd.lib.bodyscroller.panel.KeyboardFootPanel;
import com.sd.lib.bodyscroller.panel.ViewFootPanel;
import com.sd.lib.utils.FKeyboardUtil;

public class InputView extends FrameLayout implements View.OnClickListener
{
    public static final String TAG = InputView.class.getSimpleName();
    private final ViewInputBinding mBinding;

    private InputMoreView mMoreView;

    private final IFootPanel mKeyboardPanel;
    private final IFootPanel mExtPanel;

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mBinding = ViewInputBinding.inflate(LayoutInflater.from(context), this, true);

        mKeyboardPanel = new KeyboardFootPanel((Activity) context);
        mExtPanel = new ViewFootPanel(mBinding.viewExt);

        mBodyScroller.addFootPanel(mKeyboardPanel);
        mBodyScroller.addFootPanel(mExtPanel);

        mBinding.etContent.setOnClickListener(this);
        mBinding.btnMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mBinding.btnMore)
        {
            clickMore();
        } else if (v == mBinding.etContent)
        {
            mBodyScroller.setCurrentFootPanel(mKeyboardPanel);

            mBinding.viewExt.removeAllViews();
            FKeyboardUtil.show(mBinding.etContent);
        }
    }

    private InputMoreView getMoreView()
    {
        if (mMoreView == null)
            mMoreView = new InputMoreView(getContext(), null);
        return mMoreView;
    }

    private void clickMore()
    {
        if (getMoreView().getParent() != mBinding.viewExt)
        {
            switchToMore();
        } else
        {
            switchToInput();
        }
    }

    private void switchToMore()
    {
        mBodyScroller.setCurrentFootPanel(mExtPanel);

        mBinding.viewExt.removeAllViews();
        mBinding.viewExt.addView(getMoreView());
        FKeyboardUtil.hide(mBinding.etContent);
    }

    private void switchToInput()
    {
        mBodyScroller.setCurrentFootPanel(mKeyboardPanel);

        mBinding.viewExt.removeAllViews();
        FKeyboardUtil.show(mBinding.etContent);
    }

    private final FBodyScroller mBodyScroller = new FBodyScroller()
    {
        @Override
        protected void onFootHeightChanged(int oldHeight, final int newHeight)
        {
            final int delta = newHeight - oldHeight;
            Log.i(TAG, "onFootHeightChanged oldHeight:" + oldHeight + " newHeight:" + newHeight + " delta:" + delta);

            mBinding.llRoot.scrollTo(0, newHeight);
        }
    };

    private void moveByAnimator(int newHeight)
    {
        final ObjectAnimator animatorBody = ObjectAnimator.ofFloat(mBinding.llBody, View.TRANSLATION_Y, mBinding.llBody.getTranslationY(), -newHeight);
        final ObjectAnimator animatorExt = ObjectAnimator.ofFloat(mBinding.viewExt, View.TRANSLATION_Y, mBinding.viewExt.getTranslationY(), -newHeight);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorBody).with(animatorExt);
        animatorSet.setDuration(100);
        animatorSet.start();
    }
}
