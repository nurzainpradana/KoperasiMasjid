package com.nurzainpradana.koperasimasjid.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nurzainpradana.koperasimasjid.R;

public class MessageDialog {

    public AlertDialog alertDialog;

    public MessageDialog(Context mContext, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.message_popup, null);
        TextView txtView = (TextView) view.findViewById(R.id.txt_msg);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);

        txtView.setText(message);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvOk.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    public void viewMessageShow() {
        if (alertDialog != null)
            alertDialog.show();
    }

    public void viewMessageHide() {
        if(alertDialog != null)
            alertDialog.dismiss();
    }
}
