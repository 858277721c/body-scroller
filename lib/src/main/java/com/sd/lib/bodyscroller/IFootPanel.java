package com.sd.lib.bodyscroller;

public interface IFootPanel
{
    /**
     * 返回面板高度
     *
     * @return
     */
    int getHeight();

    /**
     * 面板高度变化回调
     *
     * @param callback
     */
    void setHeightChangeCallback(HeightChangeCallback callback);

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
