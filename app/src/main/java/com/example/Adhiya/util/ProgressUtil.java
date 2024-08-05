package com.example.Adhiya.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.splash.R;

public class ProgressUtil {

    // progress bar handling
    public static Dialog showProgress(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(10    ));
        dialog.setContentView(R.layout.dialog_progress);
        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(activity.getResources().getColor(R.color.Purple), PorterDuff.Mode.MULTIPLY);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
    public static Dialog spinnerProgress(Activity activity) {
        Dialog dialog=new Dialog(activity);
        dialog.setContentView(R.layout.dialog_searchable_spinner);
        dialog.getWindow().setLayout(650,800);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }
}