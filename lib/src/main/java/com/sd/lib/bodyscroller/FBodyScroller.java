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
     * 设置当前活跃的底部面板
     *
     * @param panel
     */
    public void setCurrentFootPanel(IFootPanel panel)
    {
        if (panel == null)
        {
            if (mKeyboardFootPanel == null)
            {
                mCurrentFootPanel = null;
                setFootHeight(0);
            } else
            {
                mCurrentFootPanel = mKeyboardFootPanel;
                final int height = mKeyboardFootPanel.getPanelHeight();
                if (height > 0)
                    setFootHeight(height);
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
        final int old = mFootHeight;
        if (old != height)
        {
            mFootHeight = height;
            onFootHeightChanged(old, height);
        }
    }

    protected abstract void onFootHeightChanged(int oldHeight, int newHeight);
}
