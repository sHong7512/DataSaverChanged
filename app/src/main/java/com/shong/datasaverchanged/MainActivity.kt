package com.shong.datasaverchanged

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shong.datasaverchanged.ui.theme.DataSaverChangedTheme

class MainActivity : ComponentActivity() {
    var dataSaverReceiver = DataSaverReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataSaverChangedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting {
                        val intent = Intent(
                            Settings.ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    }
                }
            }
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Checks if the device is on a metered network
        if (connectivityManager.isActiveNetworkMetered) {
            // Checks user’s Data Saver settings.
            when (connectivityManager.restrictBackgroundStatus) {
                ConnectivityManager.RESTRICT_BACKGROUND_STATUS_ENABLED ->
                    // Background data usage is blocked for this app. Wherever possible,
                    // the app should also use less data in the foreground.
                    Log.d("_sHong", "Enabled Data Saver.")
                ConnectivityManager.RESTRICT_BACKGROUND_STATUS_WHITELISTED ->
                    // The app is whitelisted. Wherever possible,
                    // the app should use less data in the foreground and background.
                    Log.d("_sHong", "The app is whitelisted.")
                ConnectivityManager.RESTRICT_BACKGROUND_STATUS_DISABLED ->
                    // Data Saver is disabled. Since the device is connected to a
                    // metered network, the app should use less data wherever possible.
                    Log.d("_sHong", "Disabled Data Saver.")
            }
        } else {
            // The device is not on a metered network.
            // Use data as required to perform syncs, downloads, and updates.
            Log.d("_sHong", "The device is not on a metered network.")
        }

        registerReceiver(
            dataSaverReceiver,
            IntentFilter(ConnectivityManager.ACTION_RESTRICT_BACKGROUND_CHANGED)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dataSaverReceiver)
    }
}

@Composable
fun Greeting(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Data Saver Changed Receiver!")
        Button(onClick = { onClick() }) {
            Text(text = "Go Setting!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DataSaverChangedTheme {
        Greeting {}
    }
}