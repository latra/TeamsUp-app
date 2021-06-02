package com.example.teamsup.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.widget.Toast;

import com.example.teamsup.R;
import com.example.teamsup.utils.ConstantsUtils;

public class NetworkReceiver extends BroadcastReceiver {

    SharedPreferences sharedPrefs;
    String networkPref;
    boolean refreshDisplay = true;
    
    public NetworkReceiver(SharedPreferences sharedPrefs) {
        this.sharedPrefs = sharedPrefs;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkPref = sharedPrefs.getString("listPref", "Wi-Fi");

        Network nw = connMgr.getActiveNetwork();

        if (nw == null) {
            refreshDisplay = true;
            Toast.makeText(context, R.string.lost_connection, Toast.LENGTH_SHORT).show();
        } else {
            NetworkCapabilities actNw = connMgr.getNetworkCapabilities(nw);
            if (actNw == null) {
                refreshDisplay = true;
                Toast.makeText(context, R.string.lost_connection, Toast.LENGTH_SHORT).show();
            } else if (ConstantsUtils.WIFI.equals(networkPref) && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))) {
                refreshDisplay = true;
                Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();
            } else if (ConstantsUtils.WIFIandDatos.equals(networkPref))
                refreshDisplay = true;
        }
    }
}
