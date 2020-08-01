package com.sd.lib.bodyscroller.panel;

public interface IFootPanel
{
    /**
     * 返回面板当前的高度
     *
     * @return
     */
    int getPanelHeight();

    /**
     * 面板高度变化回调
     *
     * @param callback
     */
    void setHeightChangeCallback(HeightChangeCallback callback);

    /**
     * 设置面板是否处于活动状态
     *
     * @param active
     */
    void setPanelActive(boolean active);

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
