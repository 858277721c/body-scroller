package com.sd.demo.body_scroller.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.demo.body_scroller.databinding.ViewInputBinding;
import com.sd.lib.bodyscroller.FBodyScroller;
import com.sd.lib.bodyscroller.ext.FKeyboardHeightKeeper;
import com.sd.lib.bodyscroller.panel.IFootPanel;
import com.sd.lib.bodyscroller.panel.KeyboardFootPanel;
import com.sd.lib.bodyscroller.panel.ViewFootPanel;
import com.sd.lib.utils.FKeyboardUtil;

public class InputView extends FrameLayout implements View.OnClickListener
{
    public static final String TAG = InputView.class.getSimpleName();

    private final ViewInputBinding mBinding;
    /** 更多View */
    private final InputMoreView mMoreView;

    /** 键盘面板 */
    private final IFootPanel mKeyboardPanel;
    /** 底部扩展面板 */
    private final IFootPanel mExtPanel;
    /** 键盘高度保持 */
    private final FKeyboardHeightKeeper mKeyboardHeightKeeper;

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mBinding = ViewInputBinding.inflate(LayoutInflater.from(context), this, true);
        mMoreView = new InputMoreView(context, null);

        // 创建面板
        mKeyboardPanel = new KeyboardFootPanel((Activity) context);
        mExtPanel = new ViewFootPanel(mBinding.viewExt);

        // 添加面板
        mBodyScroller.addFootPanel(mKeyboardPanel);
        mBodyScroller.addFootPanel(mExtPanel);

        mKeyboardHeightKeeper = new FKeyboardHeightKeeper((Activity) context)
        {
            @Override
            protected void updateViewHeight(View view, ViewGroup.LayoutParams params)
            {
                Log.i(TAG, "updateViewHeight height:" + params.height + " view:" + view);
                super.updateViewHeight(view, params);
            }
        };

        mBinding.etContent.setOnClickListener(this);
        mBinding.btnMore.setOnClickListener(this);

        // 同步键盘高度给底部容器
        mKeyboardHeightKeeper.addView(mBinding.viewExt);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mBinding.btnMore)
        {
            showModeMore();
        } else if (v == mBinding.etContent)
        {
            showModeKeyboard();
        }
    }

    /**
     * 显示更多模式
     */
    private void showModeMore()
    {
        mBodyScroller.setCurrentFootPanel(mExtPanel);

        mBinding.viewExt.removeAllViews();
        mBinding.viewExt.addView(mMoreView);
        FKeyboardUtil.hide(mBinding.etContent);
    }

    /**
     * 显示键盘输入模式
     */
    private void showModeKeyboard()
    {
        mBodyScroller.setCurrentFootPanel(mKeyboardPanel);

        mBinding.viewExt.removeAllViews();
        FKeyboardUtil.show(mBinding.etContent);
    }

    private final FBodyScroller mBodyScroller = new FBodyScroller()
    {
        @Override
        protected void onFootHeightChanged(int height)
        {
            Log.i(TAG, "onFootHeightChanged height:" + height);
            mBinding.llRoot.scrollTo(0, height);
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

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        mBodyScroller.start();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mBodyScroller.stop();
    }
}
