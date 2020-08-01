package com.sd.demo.body_scroller;

import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;

import com.sd.lib.bodyscroller.ext.FKeyboardListener;
import com.sd.lib.dialoger.impl.FDialoger;

public class KeyboardDialog extends FDialoger
{
    private static final String TAG = KeyboardDialog.class.getSimpleName();

    public KeyboardDialog(Activity activity)
    {
        super(activity);
        setContentView(R.layout.dialog_keyboard);

        setPadding(0, 0, 0, 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    private final FKeyboardListener mKeyboardListener = new FKeyboardListener(getOwnerActivity())
    {
        @Override
        protected void onKeyboardHeightChanged(int oldHeight, int newHeight)
        {
            Log.i(TAG, "FKeyboardListener onKeyboardHeightChanged oldHeight:" + oldHeight + " newHeight:" + newHeight);
        }
    };

    @Override
    protected void onStart()
    {
        super.onStart();
        mKeyboardListener.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mKeyboardListener.stop();
    }
}
