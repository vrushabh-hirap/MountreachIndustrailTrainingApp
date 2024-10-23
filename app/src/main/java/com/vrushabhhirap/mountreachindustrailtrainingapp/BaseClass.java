package com.vrushabhhirap.mountreachindustrailtrainingapp;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.appcompat.app.AppCompatActivity;

import com.vrushabhhirap.mountreachindustrailtrainingapp.Common.NetworkChangeListner;

public class BaseClass extends AppCompatActivity {


    NetworkChangeListner networkChangeListner = new NetworkChangeListner();

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListner);
    }

}
