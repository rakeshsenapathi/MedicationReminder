package com.senapathi.medicationreminder.main.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Senapathi on 31-12-2016.
 */

public class Dialog {

    private ProgressDialog progressDialog;

    public void showProgressDialog(Context context){

        if(progressDialog == null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }
    }

    public void closeProgressDialog(Context context){

        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
