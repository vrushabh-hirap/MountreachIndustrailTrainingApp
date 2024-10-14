package com.vrushabhhirap.mountreachindustrailtrainingapp.Common;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.tv.BroadcastInfoRequest;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.vrushabhhirap.mountreachindustrailtrainingapp.R;

public class NetworkChangeListner extends BroadcastReceiver {

    AppCompatButton btnRetry;
    @Override
    public void onReceive(Context context, Intent intent) {

        if(!NetworkDetails.isConnectedToInternet(context)){

            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_connection_dialog,null);
            ad.setView(layout_dialog);

            btnRetry = layout_dialog.findViewById(R.id.btn_retry_check_internet_connection);

            AlertDialog alertDialog = ad.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);

            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }else {

        }
    }
}
