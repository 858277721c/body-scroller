package com.sd.lib.bodyscroller;

import com.sd.lib.bodyscroller.panel.IFootPanel;
import com.sd.lib.bodyscroller.panel.KeyboardFootPanel;

import java.util.HashMap;
import java.util.Map;

public abstract class FBodyScroller
{
    private final Map<IFootPanel, IFootPanel.HeightChangeCallback> mMapFootPanel = new HashMap<>();

    private IFootPanel mCurrentFootPanel;
    private KeyboardFootPanel mKeyboardFootPanel;

    private int mFootHeight;

    /**
     * 返回当前底部高度
     *
     * @return
     */
    public int getFootHeight()
    {
        return mFootHeight;
    }

    /**
     * 添加底部面板
     * <p>
     * 如果面板是{@link KeyboardFootPanel}，则设置为当前面板{@link #setCurrentFootPanel(IFootPanel)}
     *
     * @param panel
     */
    public void addFootPanel(final IFootPanel panel)
    {
        if (panel == null || mMapFootPanel.containsKey(panel))
            return;

        final IFootPanel.HeightChangeCallback callback = new IFootPanel.HeightChangeCallback()
        {
            @Override
            public void onHeightChanged(int height)
            {
                if (mCurrentFootPanel == panel)
                    setFootHeight(height);
            }
        };

        mMapFootPanel.put(panel, callback);
        panel.initPanel(callback);

        if (panel instanceof KeyboardFootPanel)
        {
            mKeyboardFootPanel = (KeyboardFootPanel) panel;
            if (mCurrentFootPanel == null)
                setCurrentFootPanel(panel);
        }
    }

    /**
     * 移除底部面板
     *
     * @param panel
     */
    public void removeFootPanel(final IFootPanel panel)
    {
        final IFootPanel.HeightChangeCallback callback = mMapFootPanel.remove(panel);
        if (callback != null)
        {
            panel.releasePanel();
            if (panel == mCurrentFootPanel)
            {
                if (panel == mKeyboardFootPanel)
                    mKeyboardFootPanel = null;

                setCurrentFootPanel(null);
            }
        }
    }

    /**
     * 设置当前底部面板
     * <p>
     * 如果传入null，则内部判断是否设置过{@link KeyboardFootPanel}，如果设置过的话，自动把当前面板设置为{@link KeyboardFootPanel}
     *
     * @param panel
     */
    public void setCurrentFootPanel(IFootPanel panel)
    {
        if (panel == null)
        {
            if (mKeyboardFootPanel != null)
            {
                mCurrentFootPanel = mKeyboardFootPanel;
                final int height = mKeyboardFootPanel.getPanelHeight();
                setFootHeight(height);
            } else
            {
                mCurrentFootPanel = null;
                setFootHeight(0);
            }
            return;
        }

        if (mMapFootPanel.containsKey(panel))
        {
            mCurrentFootPanel = panel;

            final int height = panel.getPanelHeight();
            if (height > 0)
                setFootHeight(height);
        }
    }

    private void setFootHeight(int height)
    {
        if (height < 0)
            height = 0;

        final int old = mFootHeight;
        if (old != height)
        {
            mFootHeight = height;
            onFootHeightChanged(old, height);
        }
    }

    /**
     * 底部高度变化
     *
     * @param oldHeight
     * @param newHeight
     */
    protected abstract void onFootHeightChanged(int oldHeight, int newHeight);
}
