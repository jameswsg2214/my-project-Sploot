package com.work.sploot.services

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.core.app.JobIntentService
import android.widget.Toast

class Myservice:JobIntentService()
{
    val JOB_ID = 1000

    fun enqueueWork(context: Context, work: Intent)
    {

        enqueueWork(context, Myservice::class.java, JOB_ID, work)
    }
    override fun onHandleWork(intent: Intent)
    {

        val maxCount = 1000
        /**
         * Suppose we want to print 1 to 1000 number with one-second interval, Each task will take time 1 sec, So here now sleeping thread for one second.
         */
        for (i in 0 until maxCount) {
            Log.d("jgjgj", "onHandleWork: The number is: $i")

          //  val intent=Intent(this@Myservice,AlarmReceiver::class.java)

            sendBroadcast(intent)

            try {
                Thread.sleep(5000)

            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }

    }

    override fun onDestroy()
    {
        super.onDestroy()

        Log.d("destroy", "Destroy")

        /*val intent=Intent(this@Myservice,AlarmReceiver::class.java)

        sendBroadcast(intent)*/

    }

    val mHandler = Handler()

    fun toast(text: CharSequence) {
        mHandler.post(Runnable {
            Toast.makeText(
                this@Myservice,
                text,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

}