package com.nurzainpradana.koperasimasjid.util;

import android.content.Context;

public class AppUtilits {

    public static void viewMessage(Context mContext, String message) {

        com.nurzainpradana.koperasimasjid.util.MessageDialog messageDialog = null;
        if (messageDialog == null)
            messageDialog = new com.nurzainpradana.koperasimasjid.util.MessageDialog(mContext, message);
        messageDialog.viewMessageShow();
    }


}
