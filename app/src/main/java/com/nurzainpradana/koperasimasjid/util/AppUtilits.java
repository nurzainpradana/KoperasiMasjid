package com.nurzainpradana.koperasimasjid.util;

import android.content.Context;

public class AppUtilits {

    public static void viewMessage(Context mContext, String message) {

        com.nurzainpradana.koperasimasjid.Utility.MessageDialog messageDialog = null;
        if (messageDialog == null)
            messageDialog = new com.nurzainpradana.koperasimasjid.Utility.MessageDialog(mContext, message);
        messageDialog.viewMessageShow();
    }


}
