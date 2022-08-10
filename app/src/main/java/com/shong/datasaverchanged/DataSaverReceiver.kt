package com.shong.datasaverchanged

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.os.Build
import android.util.Log
import android.widget.Toast

class DataSaverReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("_sHong","onReceive : ${intent.action}")

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        Log.d("_sHong ", "DataSaverChangedBroadcastReceiver")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            when (connectivityManager.restrictBackgroundStatus) {
                RESTRICT_BACKGROUND_STATUS_ENABLED -> {
                    // Background data usage is blocked for this app. Wherever possible,
                    // the app should also use less data in the foreground.
                    Log.d("_sHong", "getRestrictBackgroundStatus : RESTRICT_BACKGROUND_STATUS_ENABLED")
                    Toast.makeText(context, "RESTRICT_BACKGROUND_STATUS_ENABLED", Toast.LENGTH_SHORT).show()
                }
                RESTRICT_BACKGROUND_STATUS_WHITELISTED -> {
                    // The app is allowed to bypass Data Saver. Nevertheless, wherever possible,
                    // the app should use less data in the foreground and background.
                    Log.d("_sHong", "getRestrictBackgroundStatus : RESTRICT_BACKGROUND_STATUS_WHITELISTED")
                    Toast.makeText(context,"RESTRICT_BACKGROUND_STATUS_WHITELISTED", Toast.LENGTH_SHORT).show()
                }
                RESTRICT_BACKGROUND_STATUS_DISABLED -> {
                    // Data Saver is disabled. Since the device is connected to a
                    // metered network, the app should use less data wherever possible.
                    Log.d("_sHong", "getRestrictBackgroundStatus : RESTRICT_BACKGROUND_STATUS_DISABLED")
                    Toast.makeText(context, "RESTRICT_BACKGROUND_STATUS_DISABLED", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d("_sHong", "getRestrictBackgroundStatus : ${connectivityManager.restrictBackgroundStatus}")
                    Toast.makeText(context,"${connectivityManager.restrictBackgroundStatus}", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Log.d("_sHong", "getRestrictBackgroundStatus : null -> under SDK Version N")
            Toast.makeText(context, "null -> under SDK Version N", Toast.LENGTH_SHORT).show()
        }
    }

}