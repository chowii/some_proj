package com.soho.sohoapp.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

import com.soho.sohoapp.R;

public class LoadingDialog {
    private final Context context;
    private ProgressDialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void show() {
        if (dialog == null) {
            dialog = ProgressDialog.show(context, null, context.getString(R.string.common_loading));
            dialog.setCancelable(false);
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
