package com.work.sploot.activities

import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.work.sploot.Entity.madicineType
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.PrefDelegate
import com.work.sploot.data.stringPref
import com.work.sploot.services.MyService
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.medicine_update.view.*
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : AppCompatActivity() {

    private var splootDB: SplootAppDB? = null
 //   var ringtoneSound:Ringtone?=null

    var ringtoneUri:Uri?=null

    var ringtoneSound:Ringtone?=null

    private var mDelayHandler: Handler? = null

    private val SPLASH_DELAY: Long = 30000

    internal val mRunnable: Runnable = Runnable {

        if (!isFinishing) {

            ringtoneSound?.stop()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alarm)

        splootDB = SplootAppDB.getInstance(this)

        ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        ringtoneSound= RingtoneManager.getRingtone(getApplicationContext(), ringtoneUri)

        if (ringtoneSound != null) {

            ringtoneSound?.play();

        }

        val intents= intent.getStringExtra("name")


        val formatter = SimpleDateFormat("dd/MM/yyyy")

        val start = formatter.parse(formatter.format(Date()))

        val end = formatter.parse(formatter.format(Date()))

        val gate_time=Date()


        Log.e("Reminder_title","$intents")

        val title=findViewById<TextView>(R.id.alarm_reminder_title)

        val name=findViewById<TextView>(R.id.alarm_reminder_name)

        val time=findViewById<TextView>(R.id.alarm_reminder_time)

        AsyncTask.execute {

            var userId by stringPref("userId", null)

            var petid by stringPref("petid", null)

            var user = userId?.toInt()

            var petId = petid?.toLong()

            try {
                val callDetails = splootDB!!.petMasterDao()

                val check=callDetails.check_one(intents.toLong())



                if (check) {


                    val data = callDetails.get_one(intents.toLong())

                    if (data.start_date!! <= start!! ) {

                        if(data.end_date!! >= start!!) {


                            if(data.repeat_type==1){

                                if((gate_time.hours == data.reminder_time!!.hours) && (gate_time.minutes==data.reminder_time!!.minutes)){

                                Log.e("alarm", "recived $data")

                                if (data.cat_type == 1) {

                                    title.text = "Vaccination"

                                } else if (data.cat_type == 2) {

                                    title.text = "Medicine"

                                } else if (data.cat_type == 3) {

                                    title.text = "Task"
                                } else if (data.cat_type == 4) {

                                    title.text = "De-worming"
                                }

                                name.text = data.task_name

                                var dates = data.reminder_time

                                var hours = dates!!.hours

                                var min = dates!!.minutes

                                var hr = ""

                                var mi = ""

                                var day = "am"

                                if (hours > 12) {
                                    day = "pm"

                                    hours = hours - 12

                                } else {
                                    hours = hours

                                    if (hours == 12) {

                                        day = "pm"

                                    } else {
                                        day = "am"
                                    }

                                }
                                if (hours < 10) {
                                    hr = "0" + hours
                                } else {
                                    hr = "" + hours
                                }

                                if (min < 10) {
                                    mi = "0$min"
                                } else {
                                    mi = "" + min
                                }

                                if (hr == "00") {

                                    hr = "12"

                                }

                                time.text = "$hr:$mi $day"

                                }

                                else{

                                    Log.e("close","hours")


                                    Log.e("close","${start.hours} == ${data.reminder_time!!.hours}) && (${start.minutes}==${data.reminder_time!!.minutes}")

                                    this.runOnUiThread {

                                        ringtoneSound?.stop()

                                        finish()

                                    }

                                }

                            }

                            else if(data.repeat_type==2){


                                Log.e("alarm", "recived $data")

                                if (data.cat_type == 1) {

                                    title.text = "Vaccination"

                                } else if (data.cat_type == 2) {

                                    title.text = "Medicine"

                                } else if (data.cat_type == 3) {

                                    title.text = "Task"
                                } else if (data.cat_type == 4) {

                                    title.text = "De-worming"
                                }

                                name.text = data.task_name

                                var dates = data.reminder_time

                                var hours = dates!!.hours

                                var min = dates!!.minutes

                                var hr = ""

                                var mi = ""

                                var day = "am"

                                if (hours > 12) {
                                    day = "pm"

                                    hours = hours - 12

                                } else {
                                    hours = hours

                                    if (hours == 12) {

                                        day = "pm"

                                    } else {
                                        day = "am"
                                    }

                                }
                                if (hours < 10) {
                                    hr = "0" + hours
                                } else {
                                    hr = "" + hours
                                }

                                if (min < 10) {
                                    mi = "0$min"
                                } else {
                                    mi = "" + min
                                }

                                if (hr == "00") {

                                    hr = "12"

                                }

                                time.text = "$hr:$mi $day"


                            }


                            else if(data.repeat_type==3){


                                if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                    Log.e("alarm", "recived $data")

                                    if (data.cat_type == 1) {

                                        title.text = "Vaccination"

                                    } else if (data.cat_type == 2) {

                                        title.text = "Medicine"

                                    } else if (data.cat_type == 3) {

                                        title.text = "Task"
                                    } else if (data.cat_type == 4) {

                                        title.text = "De-worming"
                                    }

                                    name.text = data.task_name

                                    var dates = data.reminder_time

                                    var hours = dates!!.hours

                                    var min = dates!!.minutes

                                    var hr = ""

                                    var mi = ""

                                    var day = "am"

                                    if (hours > 12) {
                                        day = "pm"

                                        hours = hours - 12

                                    } else {
                                        hours = hours

                                        if (hours == 12) {

                                            day = "pm"

                                        } else {
                                            day = "am"
                                        }

                                    }
                                    if (hours < 10) {
                                        hr = "0" + hours
                                    } else {
                                        hr = "" + hours
                                    }

                                    if (min < 10) {
                                        mi = "0$min"
                                    } else {
                                        mi = "" + min
                                    }

                                    if (hr == "00") {

                                        hr = "12"

                                    }

                                    time.text = "$hr:$mi $day"

                                }
                                else{

                                    this.runOnUiThread {

                                        ringtoneSound?.stop()

                                        finish()

                                    }

                                }

                            }
                            else  if(data.repeat_type==4){

                                if(data.start_date!!.day == start.day)
                                {


                                    if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                        Log.e("alarm", "recived $data")

                                        if (data.cat_type == 1) {

                                            title.text = "Vaccination"

                                        } else if (data.cat_type == 2) {

                                            title.text = "Medicine"

                                        } else if (data.cat_type == 3) {

                                            title.text = "Task"
                                        } else if (data.cat_type == 4) {

                                            title.text = "De-worming"
                                        }

                                        name.text = data.task_name

                                        var dates = data.reminder_time

                                        var hours = dates!!.hours

                                        var min = dates!!.minutes

                                        var hr = ""

                                        var mi = ""

                                        var day = "am"

                                        if (hours > 12) {
                                            day = "pm"

                                            hours = hours - 12

                                        } else {
                                            hours = hours

                                            if (hours == 12) {

                                                day = "pm"

                                            } else {
                                                day = "am"
                                            }

                                        }
                                        if (hours < 10) {
                                            hr = "0" + hours
                                        } else {
                                            hr = "" + hours
                                        }

                                        if (min < 10) {
                                            mi = "0$min"
                                        } else {
                                            mi = "" + min
                                        }

                                        if (hr == "00") {

                                            hr = "12"

                                        }

                                        time.text = "$hr:$mi $day"

                                    }
                                    else{

                                        this.runOnUiThread {

                                            ringtoneSound?.stop()

                                            finish()

                                        }

                                    }

                                }

                                else{

                                    ringtoneSound?.stop()

                                    finish()

                                }

                    }
                    else  if(data.repeat_type==5){

                                if(data.start_date==start){


                                    if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                        Log.e("alarm", "recived $data")

                                        if (data.cat_type == 1) {

                                            title.text = "Vaccination"

                                        } else if (data.cat_type == 2) {

                                            title.text = "Medicine"

                                        } else if (data.cat_type == 3) {

                                            title.text = "Task"
                                        } else if (data.cat_type == 4) {

                                            title.text = "De-worming"
                                        }

                                        name.text = data.task_name

                                        var dates = data.reminder_time

                                        var hours = dates!!.hours

                                        var min = dates!!.minutes

                                        var hr = ""

                                        var mi = ""

                                        var day = "am"

                                        if (hours > 12) {
                                            day = "pm"

                                            hours = hours - 12

                                        } else {
                                            hours = hours

                                            if (hours == 12) {

                                                day = "pm"

                                            } else {
                                                day = "am"
                                            }

                                        }
                                        if (hours < 10) {
                                            hr = "0" + hours
                                        } else {
                                            hr = "" + hours
                                        }

                                        if (min < 10) {
                                            mi = "0$min"
                                        } else {
                                            mi = "" + min
                                        }

                                        if (hr == "00") {

                                            hr = "12"

                                        }

                                        time.text = "$hr:$mi $day"

                                    }
                                    else{

                                        this.runOnUiThread {

                                            ringtoneSound?.stop()

                                            finish()

                                        }

                                    }

                                }
                                else{


                                    val valide=  start.time  - data.start_date!!.time


                                    val week=fun_of_week(start,data.start_date)


                                    if((week % 2 ==0) && (start.day == data.start_date!!.day)){


                                        if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                            Log.e("alarm", "recived $data")

                                            if (data.cat_type == 1) {

                                                title.text = "Vaccination"

                                            } else if (data.cat_type == 2) {

                                                title.text = "Medicine"

                                            } else if (data.cat_type == 3) {

                                                title.text = "Task"
                                            } else if (data.cat_type == 4) {

                                                title.text = "De-worming"
                                            }

                                            name.text = data.task_name

                                            var dates = data.reminder_time

                                            var hours = dates!!.hours

                                            var min = dates!!.minutes

                                            var hr = ""

                                            var mi = ""

                                            var day = "am"

                                            if (hours > 12) {
                                                day = "pm"

                                                hours = hours - 12

                                            } else {
                                                hours = hours

                                                if (hours == 12) {

                                                    day = "pm"

                                                } else {
                                                    day = "am"
                                                }

                                            }
                                            if (hours < 10) {
                                                hr = "0" + hours
                                            } else {
                                                hr = "" + hours
                                            }

                                            if (min < 10) {
                                                mi = "0$min"
                                            } else {
                                                mi = "" + min
                                            }

                                            if (hr == "00") {

                                                hr = "12"

                                            }

                                            time.text = "$hr:$mi $day"

                                        }
                                        else{

                                            this.runOnUiThread {

                                                ringtoneSound?.stop()

                                                finish()

                                            }

                                        }

                                    }
                                    else{

                                        ringtoneSound?.stop()

                                        finish()

                                    }
                                }
                    }
                    else  if(data.repeat_type==6){


                                if(data.start_date!!.date==start.date){


                                    if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                        Log.e("alarm", "recived $data")

                                        if (data.cat_type == 1) {

                                            title.text = "Vaccination"

                                        } else if (data.cat_type == 2) {

                                            title.text = "Medicine"

                                        } else if (data.cat_type == 3) {

                                            title.text = "Task"
                                        } else if (data.cat_type == 4) {

                                            title.text = "De-worming"
                                        }

                                        name.text = data.task_name

                                        var dates = data.reminder_time

                                        var hours = dates!!.hours

                                        var min = dates!!.minutes

                                        var hr = ""

                                        var mi = ""

                                        var day = "am"

                                        if (hours > 12) {
                                            day = "pm"

                                            hours = hours - 12

                                        } else {
                                            hours = hours

                                            if (hours == 12) {

                                                day = "pm"

                                            } else {
                                                day = "am"
                                            }

                                        }
                                        if (hours < 10) {
                                            hr = "0" + hours
                                        } else {
                                            hr = "" + hours
                                        }

                                        if (min < 10) {
                                            mi = "0$min"
                                        } else {
                                            mi = "" + min
                                        }

                                        if (hr == "00") {

                                            hr = "12"

                                        }

                                        time.text = "$hr:$mi $day"

                                    }
                                    else{

                                        this.runOnUiThread {

                                            ringtoneSound?.stop()

                                            finish()

                                        }

                                    }

                                }

                                else{

                                    ringtoneSound?.stop()

                                    finish()

                                }




                    }
                    else  if(data.repeat_type==7){


                                val months=fun_of_month(start,data.start_date!!)

                                //   if(select_view_data%3==0){


                                if(months%3==0 && (data.start_date!!.date == start!!.date)){


                                    if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                        Log.e("alarm", "recived $data")

                                        if (data.cat_type == 1) {

                                            title.text = "Vaccination"

                                        } else if (data.cat_type == 2) {

                                            title.text = "Medicine"

                                        } else if (data.cat_type == 3) {

                                            title.text = "Task"
                                        } else if (data.cat_type == 4) {

                                            title.text = "De-worming"
                                        }

                                        name.text = data.task_name

                                        var dates = data.reminder_time

                                        var hours = dates!!.hours

                                        var min = dates!!.minutes

                                        var hr = ""

                                        var mi = ""

                                        var day = "am"

                                        if (hours > 12) {
                                            day = "pm"

                                            hours = hours - 12

                                        } else {
                                            hours = hours

                                            if (hours == 12) {

                                                day = "pm"

                                            } else {
                                                day = "am"
                                            }

                                        }
                                        if (hours < 10) {
                                            hr = "0" + hours
                                        } else {
                                            hr = "" + hours
                                        }

                                        if (min < 10) {
                                            mi = "0$min"
                                        } else {
                                            mi = "" + min
                                        }

                                        if (hr == "00") {

                                            hr = "12"

                                        }

                                        time.text = "$hr:$mi $day"

                                    }
                                    else{

                                        this.runOnUiThread {

                                            ringtoneSound?.stop()

                                            finish()

                                        }

                                    }

                                }

                                else{

                                    ringtoneSound?.stop()

                                    finish()


                                }




                    }
                    else  if(data.repeat_type==8){


                                val months=fun_of_month(start,data.start_date!!)

                                if(months%6==0 && (data.start_date!!.date == start!!.date)){


                                    if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                        Log.e("alarm", "recived $data")

                                        if (data.cat_type == 1) {

                                            title.text = "Vaccination"

                                        } else if (data.cat_type == 2) {

                                            title.text = "Medicine"

                                        } else if (data.cat_type == 3) {

                                            title.text = "Task"
                                        } else if (data.cat_type == 4) {

                                            title.text = "De-worming"
                                        }

                                        name.text = data.task_name

                                        var dates = data.reminder_time

                                        var hours = dates!!.hours

                                        var min = dates!!.minutes

                                        var hr = ""

                                        var mi = ""

                                        var day = "am"

                                        if (hours > 12) {
                                            day = "pm"

                                            hours = hours - 12

                                        } else {
                                            hours = hours

                                            if (hours == 12) {

                                                day = "pm"

                                            } else {
                                                day = "am"
                                            }

                                        }
                                        if (hours < 10) {
                                            hr = "0" + hours
                                        } else {
                                            hr = "" + hours
                                        }

                                        if (min < 10) {
                                            mi = "0$min"
                                        } else {
                                            mi = "" + min
                                        }

                                        if (hr == "00") {

                                            hr = "12"

                                        }

                                        time.text = "$hr:$mi $day"

                                    }
                                    else{

                                        this.runOnUiThread {

                                            ringtoneSound?.stop()

                                            finish()

                                        }

                                    }

                                }
                                else{


                                    ringtoneSound?.stop()

                                    finish()



                                }





                            }
                    else  if(data.repeat_type==9){


                                if((data.start_date!!.date==start.date) && (data.start_date!!.month==start.month) ){


                                    if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                        Log.e("alarm", "recived $data")

                                        if (data.cat_type == 1) {

                                            title.text = "Vaccination"

                                        } else if (data.cat_type == 2) {

                                            title.text = "Medicine"

                                        } else if (data.cat_type == 3) {

                                            title.text = "Task"
                                        } else if (data.cat_type == 4) {

                                            title.text = "De-worming"
                                        }

                                        name.text = data.task_name

                                        var dates = data.reminder_time

                                        var hours = dates!!.hours

                                        var min = dates!!.minutes

                                        var hr = ""

                                        var mi = ""

                                        var day = "am"

                                        if (hours > 12) {
                                            day = "pm"

                                            hours = hours - 12

                                        } else {
                                            hours = hours

                                            if (hours == 12) {

                                                day = "pm"

                                            } else {
                                                day = "am"
                                            }

                                        }
                                        if (hours < 10) {
                                            hr = "0" + hours
                                        } else {
                                            hr = "" + hours
                                        }

                                        if (min < 10) {
                                            mi = "0$min"
                                        } else {
                                            mi = "" + min
                                        }

                                        if (hr == "00") {

                                            hr = "12"

                                        }

                                        time.text = "$hr:$mi $day"

                                    }
                                    else{

                                        this.runOnUiThread {

                                            ringtoneSound?.stop()

                                            finish()

                                        }

                                    }

                                }
                                else{


                                    ringtoneSound?.stop()

                                    finish()



                                }




                    }  else  if(data.repeat_type==10){


                                if(data.frequency_type_id == 1){

                                    val valide=  start.time  - data.start_date!!.time

                                    val data_responce = (valide / (1000 * 3600 * 24)).toInt()

                                    if( (data_responce) % ((data.every_frequency!!)) == 0){


                                        if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){


                                            Log.e("alarm", "recived $data")

                                            if (data.cat_type == 1) {

                                                title.text = "Vaccination"

                                            } else if (data.cat_type == 2) {

                                                title.text = "Medicine"

                                            } else if (data.cat_type == 3) {

                                                title.text = "Task"
                                            } else if (data.cat_type == 4) {

                                                title.text = "De-worming"
                                            }

                                            name.text = data.task_name

                                            var dates = data.reminder_time

                                            var hours = dates!!.hours

                                            var min = dates!!.minutes

                                            var hr = ""

                                            var mi = ""

                                            var day = "am"

                                            if (hours > 12) {
                                                day = "pm"

                                                hours = hours - 12

                                            } else {
                                                hours = hours

                                                if (hours == 12) {

                                                    day = "pm"

                                                } else {
                                                    day = "am"
                                                }

                                            }
                                            if (hours < 10) {
                                                hr = "0" + hours
                                            } else {
                                                hr = "" + hours
                                            }

                                            if (min < 10) {
                                                mi = "0$min"
                                            } else {
                                                mi = "" + min
                                            }

                                            if (hr == "00") {

                                                hr = "12"

                                            }

                                            time.text = "$hr:$mi $day"

                                        }
                                        else{

                                            this.runOnUiThread {

                                                ringtoneSound?.stop()

                                                finish()

                                            }

                                        }

                                    }

                                    else{

                                        ringtoneSound?.stop()

                                        finish()

                                    }

                                }
                                else  if(data.frequency_type_id == 2){



                                    val week=fun_of_week1(start,data.start_date)


                                    if( week % (data.every_frequency!!) == 0){

                                        //Log.e("validatesda","Startdate ${viewdatas[i].start_date}  currwent date week $start.... ${(viewdatas[i].every_frequency!!)}  ")

                                        val simpleDateformat = SimpleDateFormat("EEEE")

                                        val day = simpleDateformat.format(start)

                                        Log.e("dat1234", "" + start.date + "-----------" + day)

                                        if (data.selective_week!!.contains(day, ignoreCase = true)) {


                                            if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                                Log.e("alarm", "recived $data")

                                                if (data.cat_type == 1) {

                                                    title.text = "Vaccination"

                                                } else if (data.cat_type == 2) {

                                                    title.text = "Medicine"

                                                } else if (data.cat_type == 3) {

                                                    title.text = "Task"
                                                } else if (data.cat_type == 4) {

                                                    title.text = "De-worming"
                                                }

                                                name.text = data.task_name

                                                var dates = data.reminder_time

                                                var hours = dates!!.hours

                                                var min = dates!!.minutes

                                                var hr = ""

                                                var mi = ""

                                                var day = "am"

                                                if (hours > 12) {
                                                    day = "pm"

                                                    hours = hours - 12

                                                } else {
                                                    hours = hours

                                                    if (hours == 12) {

                                                        day = "pm"

                                                    } else {
                                                        day = "am"
                                                    }

                                                }
                                                if (hours < 10) {
                                                    hr = "0" + hours
                                                } else {
                                                    hr = "" + hours
                                                }

                                                if (min < 10) {
                                                    mi = "0$min"
                                                } else {
                                                    mi = "" + min
                                                }

                                                if (hr == "00") {

                                                    hr = "12"

                                                }

                                                time.text = "$hr:$mi $day"

                                            }
                                            else{

                                                this.runOnUiThread {

                                                    ringtoneSound?.stop()

                                                    finish()

                                                }

                                            }

                                        }

                                        else{

                                            ringtoneSound?.stop()

                                            finish()

                                        }

                                    }
                                    else{

                                        ringtoneSound?.stop()

                                        finish()

                                    }




                                }

                                else if(data.frequency_type_id == 3){


                                    val months=fun_of_month(start,data.start_date!!)

                                    if(data.start_date!!.date==start.date){

                                        Log.e("alarm", "recived $data")

                                        if((gate_time.hours == data.reminder_time!!.hours) && (gate_time.minutes==data.reminder_time!!.minutes)){

                                            Log.e("alarm", "recived $data")

                                            if (data.cat_type == 1) {

                                                title.text = "Vaccination"

                                            } else if (data.cat_type == 2) {

                                                title.text = "Medicine"

                                            } else if (data.cat_type == 3) {

                                                title.text = "Task"
                                            } else if (data.cat_type == 4) {

                                                title.text = "De-worming"
                                            }

                                            name.text = data.task_name

                                            var dates = data.reminder_time

                                            var hours = dates!!.hours

                                            var min = dates!!.minutes

                                            var hr = ""

                                            var mi = ""

                                            var day = "am"

                                            if (hours > 12) {
                                                day = "pm"

                                                hours = hours - 12

                                            } else {
                                                hours = hours

                                                if (hours == 12) {

                                                    day = "pm"

                                                } else {
                                                    day = "am"
                                                }

                                            }
                                            if (hours < 10) {
                                                hr = "0" + hours
                                            } else {
                                                hr = "" + hours
                                            }

                                            if (min < 10) {
                                                mi = "0$min"
                                            } else {
                                                mi = "" + min
                                            }

                                            if (hr == "00") {

                                                hr = "12"

                                            }

                                            time.text = "$hr:$mi $day"

                                        }

                                        else{

                                            Log.e("close","hours")


                                            Log.e("close","${start.hours} == ${data.reminder_time!!.hours}) && (${start.minutes}==${data.reminder_time!!.minutes}")

                                            this.runOnUiThread {

                                                ringtoneSound?.stop()

                                                finish()

                                            }

                                        }



                                    }
                                    else {

                                        if (((months) % (data.every_frequency!!) == 0) && (data.start_date!!.date == start!!.date)) {

                                            Log.e("alarm", "recived $data")

                                            if((gate_time.hours == data.reminder_time!!.hours) && (gate_time.minutes==data.reminder_time!!.minutes)){

                                                Log.e("alarm", "recived $data")

                                                if (data.cat_type == 1) {

                                                    title.text = "Vaccination"

                                                } else if (data.cat_type == 2) {

                                                    title.text = "Medicine"

                                                } else if (data.cat_type == 3) {

                                                    title.text = "Task"
                                                } else if (data.cat_type == 4) {

                                                    title.text = "De-worming"
                                                }

                                                name.text = data.task_name

                                                var dates = data.reminder_time

                                                var hours = dates!!.hours

                                                var min = dates!!.minutes

                                                var hr = ""

                                                var mi = ""

                                                var day = "am"

                                                if (hours > 12) {
                                                    day = "pm"

                                                    hours = hours - 12

                                                } else {
                                                    hours = hours

                                                    if (hours == 12) {

                                                        day = "pm"

                                                    } else {
                                                        day = "am"
                                                    }

                                                }
                                                if (hours < 10) {
                                                    hr = "0" + hours
                                                } else {
                                                    hr = "" + hours
                                                }

                                                if (min < 10) {
                                                    mi = "0$min"
                                                } else {
                                                    mi = "" + min
                                                }

                                                if (hr == "00") {

                                                    hr = "12"

                                                }

                                                time.text = "$hr:$mi $day"

                                            }

                                            else{

                                                Log.e("close","hours")


                                                Log.e("close","${start.hours} == ${data.reminder_time!!.hours}) && (${start.minutes}==${data.reminder_time!!.minutes}")

                                                this.runOnUiThread {

                                                    ringtoneSound?.stop()

                                                    finish()

                                                }

                                            }




                                        }

                                        else{

                                            ringtoneSound?.stop()

                                            finish()

                                        }



                                    }




                                }
                                else if(data.frequency_type_id == 4){

                                    val formatter = SimpleDateFormat("yyyy")

                                    val start_year=(formatter.format(start)).toInt()

                                    val data_year=(formatter.format(data.start_date)).toInt()


                                    if((start_year-data_year) % (data.every_frequency!!) == 0){


                                        if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                            Log.e("alarm", "recived $data")

                                            if (data.cat_type == 1) {

                                                title.text = "Vaccination"

                                            } else if (data.cat_type == 2) {

                                                title.text = "Medicine"

                                            } else if (data.cat_type == 3) {

                                                title.text = "Task"
                                            } else if (data.cat_type == 4) {

                                                title.text = "De-worming"
                                            }

                                            name.text = data.task_name

                                            var dates = data.reminder_time

                                            var hours = dates!!.hours

                                            var min = dates!!.minutes

                                            var hr = ""

                                            var mi = ""

                                            var day = "am"

                                            if (hours > 12) {
                                                day = "pm"

                                                hours = hours - 12

                                            } else {
                                                hours = hours

                                                if (hours == 12) {

                                                    day = "pm"

                                                } else {
                                                    day = "am"
                                                }

                                            }
                                            if (hours < 10) {
                                                hr = "0" + hours
                                            } else {
                                                hr = "" + hours
                                            }

                                            if (min < 10) {
                                                mi = "0$min"
                                            } else {
                                                mi = "" + min
                                            }

                                            if (hr == "00") {

                                                hr = "12"

                                            }

                                            time.text = "$hr:$mi $day"

                                        }
                                        else{

                                            this.runOnUiThread {

                                                ringtoneSound?.stop()

                                                finish()

                                            }

                                        }

                                    }

                                    else{

                                        ringtoneSound?.stop()

                                        finish()

                                    }

                                }
                            }
                            else{


                                val valide=  start.time  - data.start_date!!.time

                                val days = valide / (1000 * 3600 * 24)

                                if ((days.toInt()) % (data.repeat_type!!) == 0) {


                                    if((gate_time.hours==data.reminder_time!!.hours)&&(gate_time.minutes==data.reminder_time!!.minutes)){





                                        Log.e("alarm", "recived $data")

                                        if (data.cat_type == 1) {

                                            title.text = "Vaccination"

                                        } else if (data.cat_type == 2) {

                                            title.text = "Medicine"

                                        } else if (data.cat_type == 3) {

                                            title.text = "Task"
                                        } else if (data.cat_type == 4) {

                                            title.text = "De-worming"
                                        }

                                        name.text = data.task_name

                                        var dates = data.reminder_time

                                        var hours = dates!!.hours

                                        var min = dates!!.minutes

                                        var hr = ""

                                        var mi = ""

                                        var day = "am"

                                        if (hours > 12) {
                                            day = "pm"

                                            hours = hours - 12

                                        } else {
                                            hours = hours

                                            if (hours == 12) {

                                                day = "pm"

                                            } else {
                                                day = "am"
                                            }

                                        }
                                        if (hours < 10) {
                                            hr = "0" + hours
                                        } else {
                                            hr = "" + hours
                                        }

                                        if (min < 10) {
                                            mi = "0$min"
                                        } else {
                                            mi = "" + min
                                        }

                                        if (hr == "00") {

                                            hr = "12"

                                        }

                                        time.text = "$hr:$mi $day"

                                    }
                                    else{

                                        this.runOnUiThread {

                                            ringtoneSound?.stop()

                                            finish()

                                        }

                                    }

                                }
                                else{

                                    ringtoneSound?.stop()

                                    finish()

                                }

                            }

                        }

                        else{

                            Log.e("close","end date")

                            this.runOnUiThread {

                                ringtoneSound?.stop()

                                finish()

                            }
                        }
                    }
                    else{

                        Log.e("close","Start date")
                        this.runOnUiThread {

                            ringtoneSound?.stop()

                            finish()

                        }
                    }

                }

                else{

                    Log.e("close","datanot found")

                    this.runOnUiThread {

                        ringtoneSound?.stop()

                        finish()

                    }

                }

            }
            catch (e: Exception) {

                val s = e.message

                Log.e("Error", s)

            }

        }


        alarm_close.setOnClickListener {


            ringtoneSound?.stop()

           // val intent = Intent(this, firstpage::class.java)

           // startActivity(intent)

            finish()

        }

        mDelayHandler = Handler()

        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)



    }

    private fun fun_of_week1(startdate: Date?, enddate: Date?): Int {

        var a=startdate
        var b =enddate


        val cal = GregorianCalendar();
        cal.setTime(a);

        val wek1=cal.get(Calendar.WEEK_OF_YEAR)

        cal.setTime(b);
        val wek2=cal.get(Calendar.WEEK_OF_YEAR)


        val wek=wek2-wek1



        var weeks = 0;
        while (cal.getTime().before(b)) {
            // add another week
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            weeks++;
        }

        Log.e("week old",""+weeks)

        Log.e("week new",""+wek)
        return wek;



    }

    private fun fun_of_week(currentDate: Date?, date: Date?): Int {

        var a=date
        var b =currentDate

        if (b!!.before(a)) {
            return -fun_of_week(b, a);
        }
        a = resetTime(a!!);

        b = resetTime(b!!)


        val cal = GregorianCalendar();
        cal.setTime(a);
        var weeks = 0;
        while (cal.getTime().before(b)) {
            // add another week
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            weeks++;
        }
        return weeks;



    }


    private fun resetTime(d: Date?): Date? {

        val cal = GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime()

    }

    private fun fun_of_month(date1: Date, date2: Date?): Int {

        var a=date1

        var b=date2

        val cal = Calendar.getInstance();
        if (a.before(b)) {
            cal.setTime(a);
        } else {
            cal.setTime(b);
            b = a;
        }
        var c = 0;
        while (cal.getTime().before(b)) {
            cal.add(Calendar.MONTH, 1)
            c++;
        }
        return c - 1


    }

    override fun onDestroy() {
        //ringtoneSound?.stop()

        super.onDestroy()
    }

}
