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

    private boolean mIsStarted;
    private int mFootHeight;

    /**
     * 是否已经开始监听
     *
     * @return
     */
    public boolean isStarted()
    {
        return mIsStarted;
    }

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
     * 返回当前底部面板
     *
     * @return
     */
    public IFootPanel getCurrentFootPanel()
    {
        return mCurrentFootPanel;
    }

    /**
     * 开始监听
     */
    public void start()
    {
        if (!mIsStarted)
        {
            mIsStarted = true;
            notifyHeightChanged();
        }
    }

    /**
     * 停止监听
     */
    public void stop()
    {
        mIsStarted = false;
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
     * 清空底部面板
     */
    public void clearFootPanel()
    {
        clearFootPanel(true);
    }

    private void clearFootPanel(boolean removeCurrent)
    {
        if (mCurrentFootPanel != null)
        {
            if (removeCurrent)
                removeFootPanel(mCurrentFootPanel);

            mCurrentFootPanel = null;
        }

        for (IFootPanel item : mMapFootPanel.keySet())
        {
            item.releasePanel();
        }
        mMapFootPanel.clear();

        mKeyboardFootPanel = null;
        mFootHeight = 0;
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

    /**
     * 设置底部高度
     *
     * @param height
     */
    private void setFootHeight(int height)
    {
        if (height < 0)
            height = 0;

        final int old = mFootHeight;
        if (old != height)
        {
            mFootHeight = height;
            notifyHeightChanged();
        }
    }

    /**
     * 通知底部高度变化
     */
    private void notifyHeightChanged()
    {
        if (mIsStarted)
            onFootHeightChanged(mFootHeight);
    }

    /**
     * 底部高度变化
     *
     * @param height
     */
    protected abstract void onFootHeightChanged(int height);

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        clearFootPanel(false);
    }
}
