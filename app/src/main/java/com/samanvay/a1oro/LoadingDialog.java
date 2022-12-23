package com.samanvay.a1oro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    public Activity activity;
    public AlertDialog dialog;
    public LoadingDialog(Activity myActivity){
        activity=myActivity;
    }

    @SuppressLint("InflateParams")
    public void startLoadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.loading_dialog,null));
        builder.setCancelable(false);
        dialog=builder.create();
        dialog.show();
    }
    public void dismissDialog(){
     dialog.dismiss();
    }
}
