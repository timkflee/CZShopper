package com.dev.tim.czshopper.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Tim on 12/28/2015.
 */
public class ToastFactory {

    public static void newToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
