package com.dev.tim.czshopper.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.dev.tim.czshopper.R;

/**
 * Created by Tim on 12/27/2015.
 */
public class DialogFactory {

    public static AlertDialog newDialog(Activity context, String message, DialogInterface.OnClickListener positiveListener) {
        return newDialog(context, message, positiveListener, null);
    }

    public static AlertDialog newDialog(Activity context, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);


        builder.setPositiveButton(R.string.ok, positiveListener);

        if (negativeListener != null)
            builder.setNegativeButton(context.getText(R.string.cancel), negativeListener);

        return builder.create();
    }

    public static ProgressDialog newProgressDialog(Activity context, String message, boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);

        return dialog;
    }
}
