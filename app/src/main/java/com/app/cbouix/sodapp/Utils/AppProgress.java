package com.app.cbouix.sodapp.Utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.app.cbouix.sodapp.R;

/**
 * Created by CBouix on 25/03/2017.
 */
public class AppProgress {
    static ProgressDialog ringProgressDialog = null;

    public static void showProgress(Context context){
        ringProgressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.mensaje_espera), true);
    }

    public static void hideProfress(){
        if(ringProgressDialog != null){
            ringProgressDialog.hide();
        }
    }
}
