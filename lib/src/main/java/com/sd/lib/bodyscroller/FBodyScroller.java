package com.sd.lib.bodyscroller;

import java.util.HashMap;
import java.util.Map;

public abstract class FBodyScroller
{
    private final Map<IFootPanel, IFootPanel.HeightChangeCallback> mMapFootPanel = new HashMap<>();

    private IFootPanel mActiveFootPanel;
    private int mFootHeight;

    /**
     * 底部高度
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
                if (mActiveFootPanel == panel)
                    setFootHeight(height);
            }
        };

        panel.setHeightChangeCallback(callback);
        mMapFootPanel.put(panel, callback);

        if (panel instanceof KeyboardFootPanel)
            ((KeyboardFootPanel) panel).start();
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
            panel.setHeightChangeCallback(null);
            if (panel instanceof KeyboardFootPanel)
                ((KeyboardFootPanel) panel).stop();
        }
    }

    /**
     * 设置当前活跃的底部面板
     *
     * @param panel
     */
    public void setActiveFootPanel(IFootPanel panel)
    {
        if (panel == null)
        {
            mActiveFootPanel = null;
            setFootHeight(0);
            return;
        }

        if (mMapFootPanel.containsKey(panel))
        {
            mActiveFootPanel = panel;

            final int height = panel.getHeight();
            setFootHeight(height);
        }
    }

    private void setFootHeight(int height)
    {
        final int old = mFootHeight;
        if (old != height)
        {
            mFootHeight = height;

            final int delta = height - old;
            if (delta > 0)
            {
                moveBodyUp(delta);
            } else
            {
                moveBodyDown(Math.abs(delta));
            }
        }
    }

    /**
     * 把body往上移动
     *
     * @param delta
     */
    protected abstract void moveBodyUp(int delta);

    /**
     * 把body往下移动
     *
     * @param delta
     */
    protected abstract void moveBodyDown(int delta);
}
