package com.sd.lib.bodyscroller.panel;

import com.sd.lib.bodyscroller.FBodyScroller;

public interface IFootPanel
{
    /**
     * 返回面板当前的高度
     *
     * @return
     */
    int getPanelHeight();

    /**
     * 当面板被添加到{@link FBodyScroller}的时候触发
     *
     * @param callback
     */
    void initPanel(HeightChangeCallback callback);

    /**
     * 当面板从{@link FBodyScroller}移除的时候触发
     */
    void releasePanel();

    interface HeightChangeCallback
    {
        /**
         * 高度变化
         *
         * @param height
         */
        void onHeightChanged(int height);
    }
}
