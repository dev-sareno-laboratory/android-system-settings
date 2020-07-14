package com.example.webviewexperiment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.*
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            Log.i(TAG, "Click")

            showPermissionsDialog()
        }
    }

    private fun showPermissionsDialog() {
        if (System.canWrite(applicationContext)) {
            Log.i(TAG, "Already has permission")

            // Using SYSTEM SETTINGS
            //working
            val brightnessWasSet = System.putInt(contentResolver, System.SCREEN_BRIGHTNESS, 50)
            if (brightnessWasSet) {
                Log.i(TAG, "Brightness was set")
            }

            // Using CONFIGURATION
            val config = Configuration()

            //30% smaller
            //working
            config.fontScale = 0.7f

            //lowest density
            //not working
            config.densityDpi = DisplayMetrics.DENSITY_LOW

            // works on Android 10
            // not working on Android 9
            // not tested on Android <8
            val customConfigWasSet = System.putConfiguration(contentResolver, config)
            if (customConfigWasSet) {
                Log.i(TAG, "Custom config was set")
            }

        } else {
            Log.i(TAG, "No permission")
            val intent = Intent(ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:$packageName"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            baseContext.startActivity(intent)
        }
    }
}
