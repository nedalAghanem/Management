package com.android.management.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.android.management.R;
import com.android.management.controller.activities.LoginActivity;
import com.orhanobut.hawk.Hawk;

public class Constants {

    public final static String LANGUAGE = "language";
    public final static String LANGUAGE_TYPE = "language_type";
    public final static String USER = "user";
    public final static String IS_LOGIN = "is_login";

    public final static String KEY = "key";
    public final static String KEY_2 = "key2";
    public final static String TYPE = "type";
    public final static String TYPE_ADD = "type_add";
    public final static String TYPE_EDIT = "type_edit";
    public final static String TYPE_MODEL = "type_mode;";

    public final static int REQUEST_GALLERY_CODE = 10001;

    public static void logout(Activity context) {
        Hawk.deleteAll();
        Toast.makeText(context, context.getString(R.string.logout_successfully), Toast.LENGTH_SHORT).show();
        context.startActivity(new Intent(context, LoginActivity.class));
        context.finish();
    }


    private void dialogDelete(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_item)
                .setMessage(R.string.delete_item_sure)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Continue with delete operation
                })
                .setNegativeButton(android.R.string.no, (dialog, i) -> {
                    dialog.dismiss();
                })
                .setNeutralButton(android.R.string.cancel, (dialog, i) -> {
                    dialog.dismiss();
                })
                .setIcon(R.drawable.ic_delete)
                .show();
    }

}
