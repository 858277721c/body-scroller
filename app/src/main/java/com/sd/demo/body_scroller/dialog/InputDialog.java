package com.sd.demo.body_scroller.dialog;

import android.app.Activity;
import android.view.WindowManager;

import com.sd.demo.body_scroller.R;
import com.sd.lib.dialoger.impl.FDialoger;

public class InputDialog extends FDialoger
{
    public InputDialog(Activity activity)
    {
        super(activity);
        setContentView(R.layout.dialog_input);

        setPadding(0, 0, 0, 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }
}
