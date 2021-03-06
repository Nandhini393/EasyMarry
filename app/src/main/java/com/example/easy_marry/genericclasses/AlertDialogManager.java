package com.example.easy_marry.genericclasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by Pc on 08-03-2016.
 */
public class AlertDialogManager {

    @SuppressWarnings("deprecation")
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if (status != null)
            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        // Showing Alert Message
        alertDialog.show();
    }



    @SuppressWarnings("deprecation")
    public void showAlertDialog(Context context, String string, Boolean status) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title

        // Setting Dialog Message
        alertDialog.setMessage(string);

        if (status != null)
            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

        // Showing Alert Message
        alertDialog.show();

    }
}