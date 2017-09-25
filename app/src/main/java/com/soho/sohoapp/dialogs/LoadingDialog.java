package com.soho.sohoapp.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

@Deprecated
public class LoadingDialog {
    private final Context context;
    private ProgressDialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void show(String message) {
        if (dialog == null) {
            dialog = ProgressDialog.show(context, null, message);
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
