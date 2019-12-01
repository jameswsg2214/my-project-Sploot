package com.work.sploot.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent)
    {
        val mIntent = Intent(context, MyService::class.java)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

           //context.startForegroundService(mIntent)
        }else
        {
            //context.startService(mIntent)
        }
    }
}
