package com.nurzainpradana.koperasimasjid.Utility;

import android.content.Context;

public class AppUtilits {

    public static void viewMessage(Context mContext, String message) {

        MessageDialog messageDialog = null;
        if (messageDialog == null)
            messageDialog = new MessageDialog(mContext, message);
        messageDialog.viewMessageShow();
    }


}
