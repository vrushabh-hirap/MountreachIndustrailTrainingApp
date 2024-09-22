package com.vrushabhhirap.mountreachindustrailtrainingapp.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkDetails {
    public static boolean isConnectedToInternet(Context context){

        //ConnectivityManger class can go to the driver to check if the data is connected or not

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            //Internet Bhetla
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if( networkInfo != null){
                for(int i = 0;i<networkInfo.length;i++){
                    if(networkInfo[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }

        }
        return false;


        //here is what happening the networkchangelistener is caller the braodcastreciver and this is calling onRecivier and that is calling NetworkDetails calss and it is calling is connetexto interted funtion and it is calling the connectivitymanager to check the intenent connection

    }
}
