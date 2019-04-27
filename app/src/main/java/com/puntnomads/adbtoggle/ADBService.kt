package com.puntnomads.adbtoggle

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.provider.Settings

class ADBService : Service() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Send a notification that service is started
        println("Service started.")

        // Do a periodic task
        mHandler = Handler()
        mRunnable = Runnable { turnADBOff() }
        mHandler.postDelayed(mRunnable, 3000)

        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Service destroyed.")
        Settings.Global.putInt(getContentResolver(),Settings.Global.ADB_ENABLED, 1);
        mHandler.removeCallbacks(mRunnable)
    }

    // Custom method to do a task
    private fun turnADBOff() {
        if(Settings.Global.ADB_ENABLED === "adb_enabled"){
            Settings.Global.putInt(getContentResolver(),Settings.Global.ADB_ENABLED, 0);
        }
        mHandler.postDelayed(mRunnable, 3000)
    }
}