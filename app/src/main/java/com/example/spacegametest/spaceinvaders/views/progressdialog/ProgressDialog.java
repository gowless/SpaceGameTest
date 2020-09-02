package com.example.spacegametest.spaceinvaders.views.progressdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.spacegametest.R;


public class ProgressDialog {

    Activity activity;
    AlertDialog dialog;

    public ProgressDialog(Activity myActivity){
        activity = myActivity;
    }



    public void StartProgressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){

        dialog.dismiss();
    }

}
