package com.work.sploot.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import com.work.sploot.Entity.petMasterEntity
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.activities.MainActivity
import com.work.sploot.data.stringPref
import java.util.*
import java.text.SimpleDateFormat



class MyService : Service()
{


    var counter = 0
    private var splootDB: SplootAppDB? = null


    val JOB_ID = 1000

    val NOTIFICATION_ID = 666

    var notification: Notification? = null

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    var oldTime: Long = 0


    override fun onBind(p0: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {

        super.onCreate()

        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val notification: Notification = Notification.Builder(this)
            .setContentTitle(getText(R.string.app_name))
            .setContentText(getText(R.string.app_name))
            .setSmallIcon(R.drawable.ic_sploot_logo2)
            .setContentIntent(pendingIntent)
            .setPriority(Notification.PRIORITY_MAX)
            .setChannelId(Notification.EXTRA_NOTIFICATION_ID)
            .setTicker(getText(R.string.app_name))
            .build()

        //notification = NotificationCompat.Builder(this,NotificationChannel.DEFAULT_CHANNEL_ID).setSmallIcon(R.drawable.ic_sploot_logo2).setContentTitle("The unseen blade").setContentText("If you see me, congrats to you.").build()

        //startForeground NOTIFICATION_ID, notification)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

        {
            startForeground(NOTIFICATION_ID, notification);

            Log.d("start123467", "34567888")
        }



    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {


        startTimer()



        return START_STICKY
    }


   /* fun enqueueWork(context: Context, work: Intent) {

        enqueueWork(context, MyService::class.java, JOB_ID, work)
    }
*//*    override fun onHandleWork(intent: Intent)
    {

        val maxCount = 86400
        *//**
         * Suppose we want to print 1 to 1000 number with one-second interval, Each task will take time 1 sec, So here now sleeping thread for one second.
         *//*
        for (i in 0 until maxCount) {
            Log.d("jgjgj", "onHandleWork: The number is: $i")

            val dateInString = Date()

            splootDB = SplootAppDB.getInstance(this@MyService)

            checkdbreminder(dateInString,this@MyService)




            try {
                Thread.sleep(1000)


                Log.i("in timer", "in timer ++++  " + i)




            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }

    }*/

    override fun onDestroy()
    {
        super.onDestroy()
        Log.d("destroy", "Destroy")

        stopForeground(true)


     val intent=Intent(this,MyReceiver::class.java)

        sendBroadcast(intent)

    }

    val mHandler = Handler()

    fun toast(text: CharSequence) {
        mHandler.post(Runnable {
            Toast.makeText(
                this@MyService,
                text,
                Toast.LENGTH_SHORT
            ).show()
        })
    }



    fun startTimer() {
        //set a new Timer
        timer = Timer()

        initializeTimerTask()

        //schedule the timer, to wake up every 1 second
        timer!!.schedule(timerTask, 1000, 5000)
    }
    fun initializeTimerTask() {
        timerTask = object : TimerTask() {
            @SuppressLint("MissingPermission")
            override fun run() {

                val dateInString = Date()

               // toast(dateInString.toString())

                val intent=Intent(this@MyService,MyReceiver::class.java)

                sendBroadcast(intent)

                Log.d("start1234", ""+dateInString)



               // checkdbreminder(dateInString,this@MyService)

            }
        }
    }

    /**
     * not needed
     */

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun checkdbreminder(start: Date,context: Context) {

        var end=start

        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var user= userId?.toInt()
            try {

                val callDetails = splootDB!!.petMasterDao()


                val viewdata=callDetails.getAll_cat_check()

                if(viewdata){

                    //   var viewdatas=callDetails.getcatmonth(start!!,end!!,userId!!,petid!!)

                    val viewdatas=callDetails.getAll_cat_()

                    Log.e("length",""+viewdatas.size)

                    for(i in 0 ..viewdatas.size-1) {

                        Log.e(
                            "date size",
                            ""+i
                        )

                        if(viewdatas[i].userId==userId){

                            if(viewdatas[i].petId==petid){

                                if (viewdatas[i].start_date!! <= start!! ) {

                                    Log.e(
                                        "start date if",
                                        "${viewdatas[i].start_date}..............${viewdatas[i].end_date}."
                                    )

                                    if(viewdatas[i].end_date!! >= end!!) {

                                        if (viewdatas[i].cat_type!=4) {

                                            if (viewdatas[i].repeat_type == 1) {


                                                val current_time_hr=""+start.hours+":"+start.minutes+":"+start.seconds

                                                val select_time_hr=""+viewdatas[i].reminder_time!!.hours+":"+viewdatas[i].reminder_time!!.minutes+":00"



                                                val intent=Intent(this@MyService,Alertreciver::class.java)

                                                sendBroadcast(intent)




                                              /*  if (viewdatas[i].cat_type == 1) {


                                                    view1.post(Runnable {

                                                        view1.visibility = View.VISIBLE
                                                    })

                                                } else if (viewdatas[i].cat_type == 2) {
                                                    view3.post(Runnable {
                                                        view3.visibility = View.VISIBLE
                                                    })

                                                }*/


                                            }
                                            else if (viewdatas[i].repeat_type == 2) {



                                            }
                                            else if (viewdatas[i].repeat_type == 3) {

                                              /*  if (viewdatas[i].cat_type == 1) {


                                                    view1.post(Runnable {

                                                        view1.visibility = View.VISIBLE
                                                    })

                                                } else if (viewdatas[i].cat_type == 2) {
                                                    view3.post(Runnable {
                                                        view3.visibility = View.VISIBLE
                                                    })

                                                }
*/

                                            }
                                            else if (viewdatas[i].repeat_type == 4) {

                                                if(viewdatas[i].start_date!!.day==start.day){
/*
                                                    if (viewdatas[i].cat_type == 1) {

                                                        view1.post(Runnable {

                                                            view1.visibility = View.VISIBLE
                                                        })

                                                    } else if (viewdatas[i].cat_type == 2) {
                                                        view3.post(Runnable {
                                                            view3.visibility = View.VISIBLE
                                                        })

                                                    }*/



                                                }



                                            }
                                            else if (viewdatas[i].repeat_type == 5) {

                                                if(viewdatas[i].start_date==start){

/*
                                                    if (viewdatas[i].cat_type == 1) {

                                                        view1.post(Runnable {

                                                            view1.visibility = View.VISIBLE
                                                        })

                                                    } else if (viewdatas[i].cat_type == 2) {
                                                        view3.post(Runnable {
                                                            view3.visibility = View.VISIBLE
                                                        })

                                                    }*/

                                                }
                                                else{

                                                    var select_view_data=start.date-viewdatas[i].start_date!!.date

                                                    if(select_view_data%14==0){


                                                      /*  if (viewdatas[i].cat_type == 1) {

                                                            view1.post(Runnable {

                                                                view1.visibility = View.VISIBLE
                                                            })

                                                        } else if (viewdatas[i].cat_type == 2) {
                                                            view3.post(Runnable {
                                                                view3.visibility = View.VISIBLE
                                                            })

                                                        }*/

                                                    }
                                                }
                                            }
                                            else if (viewdatas[i].repeat_type == 6) {

                                                if(viewdatas[i].start_date!!.date==start.date){

                                                  /*  if (viewdatas[i].cat_type == 1) {

                                                        view1.post(Runnable {

                                                            view1.visibility = View.VISIBLE
                                                        })

                                                    } else if (viewdatas[i].cat_type == 2) {
                                                        view3.post(Runnable {
                                                            view3.visibility = View.VISIBLE
                                                        })

                                                    }*/



                                                }


                                            }
                                            else if (viewdatas[i].repeat_type == 7) {


                                                if((viewdatas[i].start_date!!.month+3) == start.month){

                                                  /*  if (viewdatas[i].cat_type == 1) {

                                                        view1.post(Runnable {

                                                            view1.visibility = View.VISIBLE
                                                        })

                                                    }
                                                    else if (viewdatas[i].cat_type == 2) {
                                                        view3.post(Runnable {
                                                            view3.visibility = View.VISIBLE
                                                        })

                                                    }*/

                                                }

                                            }
                                            else if (viewdatas[i].repeat_type == 8) {

                                                if((viewdatas[i].start_date!!.month+6)==start.month){

                                                 /*   if (viewdatas[i].cat_type == 1) {

                                                        view1.post(Runnable {

                                                            view1.visibility = View.VISIBLE
                                                        })

                                                    } else if (viewdatas[i].cat_type == 2) {
                                                        view3.post(Runnable {
                                                            view3.visibility = View.VISIBLE
                                                        })

                                                    }*/

                                                }



                                            }
                                            else if (viewdatas[i].repeat_type == 9) {


                                                if((viewdatas[i].start_date!!.date==start.date)&& (viewdatas[i].start_date!!.month==start.month) ){

                                                   /* if (viewdatas[i].cat_type == 1) {

                                                        view1.post(Runnable {

                                                            view1.visibility = View.VISIBLE
                                                        })

                                                    } else if (viewdatas[i].cat_type == 2) {
                                                        view3.post(Runnable {
                                                            view3.visibility = View.VISIBLE
                                                        })

                                                    }*/

                                                }

                                            }

                                            else if (viewdatas[i].repeat_type == 10) {

                                                if(viewdatas[i].frequency_type_id == 1){

                                                    val data_responce=(start.date-viewdatas[i].start_date!!.date)



                                                    Log.e("responce_data","$data_responce....  ${start.date}..... ${viewdatas[i].start_date!!.date} ")


                                                    if( (data_responce) % ((viewdatas[i].every_frequency!!) + 1) == 0){

                                                        Log.e("responce_if_if","$data_responce....  ${start.date}..... ${viewdatas[i].start_date!!.date} ")

                                                        Log.e("responce_freq","${viewdatas[i].every_frequency!!}")

                                                    /*    if (viewdatas[i].cat_type == 1) {

                                                            view1.post(Runnable {

                                                                view1.visibility = View.VISIBLE
                                                            })

                                                        } else if (viewdatas[i].cat_type == 2) {
                                                            view3.post(Runnable {
                                                                view3.visibility = View.VISIBLE
                                                            })

                                                        }*/



                                                    }

                                                    else{

                                                        Log.e("responce_if_else","$data_responce....  ${start.date}..... ${viewdatas[i].start_date!!.date} ")
                                                    }

                                                }
                                                else if(viewdatas[i].frequency_type_id==2){


                                                    if((start.day-viewdatas[i].start_date!!.day) % (viewdatas[i].every_frequency!! + 1 ) == 0){

                                                        Log.e("week","${start.day}")

                                                        val simpleDateformat = SimpleDateFormat("EEEE")

                                                        // the day of the week spelled out completely

                                                        val day = simpleDateformat.format(start)

                                                        Log.e("dat1234", "" + start.date + "-----------" + day)

                                                        if (viewdatas[i].selective_week!!.contains(day, ignoreCase = true)) {

                                                          /*  if (viewdatas[i].cat_type == 1) {

                                                                view1.post(Runnable {

                                                                    view1.visibility = View.VISIBLE

                                                                })

                                                            } else if (viewdatas[i].cat_type == 2) {

                                                                view3.post(Runnable {

                                                                    view3.visibility = View.VISIBLE

                                                                })

                                                            }*/

                                                        }

                                                    }

                                                }
                                                else if(viewdatas[i].frequency_type_id==3){

                                                    if((start.month-viewdatas[i].start_date!!.month) % (viewdatas[i].every_frequency!! + 1 ) == 0){


                                                       /* if (viewdatas[i].cat_type == 1) {

                                                            view1.post(Runnable {

                                                                view1.visibility = View.VISIBLE
                                                            })

                                                        } else if (viewdatas[i].cat_type == 2) {
                                                            view3.post(Runnable {
                                                                view3.visibility = View.VISIBLE
                                                            })

                                                        }*/




                                                    }


                                                }
                                                else if(viewdatas[i].frequency_type_id==4){

                                                    if((start.year-viewdatas[i].start_date!!.year) % (viewdatas[i].every_frequency!! + 1 ) == 0){


                                                      /*  if (viewdatas[i].cat_type == 1) {

                                                            view1.post(Runnable {

                                                                view1.visibility = View.VISIBLE
                                                            })

                                                        } else if (viewdatas[i].cat_type == 2) {
                                                            view3.post(Runnable {
                                                                view3.visibility = View.VISIBLE
                                                            })

                                                        }*/
                                                    }
                                                }
                                            }
                                        }
                                        else{


                                            if ((start.date - viewdatas[i].start_date!!.date) % (viewdatas[i].repeat_type!!) == 0) {
/*
                                                view2.post(Runnable {

                                                    view2.visibility = View.VISIBLE
                                                })*/

                                            }
                                        }

                                    }

                                } else {
                                    Log.e(
                                        "date else",
                                        "${viewdatas[i].start_date}..............${viewdatas[i].end_date}."
                                    )

                                }
                            }


                        }


                    }


                    // viewdatas.medicineType

                    // Log.e("viewsdata11","$viewdatas")
                }

                else{
                    Log.e("no date"," no data in this month")
                }




            } catch (e: Exception) {
                val s = e.message
                Log.e("Error",s)
            }
        }


    }
    fun stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }
}
