package com.work.sploot.activities

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.work.sploot.Entity.madicineType
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import com.work.sploot.services.AlarmReceiver
import kotlinx.android.synthetic.main.calender.*
import kotlinx.android.synthetic.main.medicine_update.view.*
import kotlinx.android.synthetic.main.medicine_update.view.task_close
import kotlinx.android.synthetic.main.select_frequency_dialog.*
import kotlinx.android.synthetic.main.week_dialog_picker.*
import java.text.SimpleDateFormat
import java.util.*


class add_task_fregment:Fragment() {

    private var splootDB: SplootAppDB? = null

    var Startdate:Date?=null

    var Enddate:Date?=null

    var viewers:View?=null

    var type_name:String?=null

    var reminder_time:Date?=null

    var repeat_type:Int?=1

    var frequency_type_id:Int?=null

    var every_frequency:Int?=null

    var selective_week:String?=null

    var flow_type:Int?=null

    var dialog:Dialog?=null


    companion object {

        var viewdata: View? =null

        var cont: FragmentActivity?=null

        var cat_type:Int?=null

        fun newInstance(view: View?, mContext: FragmentActivity?, type: Int
        ): add_task_fregment {
            viewdata=view
            cont=mContext

            cat_type=type

            return add_task_fregment()
        }
    }






    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var select_date by stringPref("select_date", null)


        val curentdate=Date()

        Startdate=curentdate

        Enddate=curentdate



        var ischeck_custom=true

        val formatter = SimpleDateFormat("dd/MM/yyyy")
//
//        Startdate=formatter.parse(curentdate)
//
//        Enddate=formatter.parse(curentdate)



        val views = inflater.inflate(R.layout.medicine_update, container, false)

        views.add_task_viewer.visibility=View.VISIBLE

        dialog= Dialog(views.context)




        views.task_close.setOnClickListener {

            val inputMethodManager =
                views.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

            val mContext = activity

            val fm = cont?.supportFragmentManager

            val transaction = fm?.beginTransaction()

            transaction?.replace(R.id.task_frame,taskfragmentActivity.newInstance(viewdata!!, cont!!))

            transaction?.commit()
        }





            views.medicine_name.hint="Enter Task Name"



        splootDB = SplootAppDB.getInstance(views.context)

        viewers=views

        views.get_time.addOnDateChangedListener { displayed, date ->

            var dates=date

            reminder_time=date

            var hours=dates.hours

            var min=dates.minutes

            var hr=""

            var mi=""

            var day="am"

            if(hours>12){
                day="pm"
                hours =hours- 12
            } else{
                hours=hours

                if(hours==12){

                    day="pm"

                }
                else{
                    day="am"
                }

            }
            if(hours<10)
            {
                hr="0"+hours
            }else
            {
                hr=""+hours
            }

            if(min<10)
            {
                mi  = "0$min"
            }else
            {
                mi=""+min
            }

            if(hr=="00"){

                hr="12"

            }

            //  textViewName.text ="$hr:$mi $day"

            views.reminder_time.text="$hr:$mi $day"
        }

        var isrepeat=true

        views.repeat_time.setOnClickListener {

            if(isrepeat){

                views.repeat_list_view_layout.visibility=View.VISIBLE

                isrepeat=false
            }

            else{

                views.repeat_list_view_layout.visibility=View.GONE

                isrepeat=true
            }


        }

        views.never_check.setOnClickListener {

            repeat_type=1

            Enddate=Startdate

            ischeck_custom=true

            views.selected_Type.text="Never"

            views.end_repeat_layout.visibility=View.GONE

            views.never_select.visibility=View.VISIBLE

            views.hourly_select.visibility=View.INVISIBLE

            views.Daily_select.visibility=View.INVISIBLE

            views.weekly_select.visibility=View.INVISIBLE

            views.Fortnightly_select.visibility=View.INVISIBLE

            views.Monthly_select.visibility=View.INVISIBLE

            views.Every3m_select.visibility=View.INVISIBLE

            views.every6m_select.visibility=View.INVISIBLE

            views.yearly_select.visibility=View.INVISIBLE

            views.custom_layout_view.visibility=View.GONE

        }

        views.hourly_check.setOnClickListener {

            repeat_type=2

            views.selected_Type.text="Hourly"

            ischeck_custom=true

            views.end_repeat_layout.visibility=View.VISIBLE

            views.never_select.visibility=View.INVISIBLE

            views.hourly_select.visibility=View.VISIBLE

            views.Daily_select.visibility=View.INVISIBLE

            views.weekly_select.visibility=View.INVISIBLE

            views.Fortnightly_select.visibility=View.INVISIBLE

            views.Monthly_select.visibility=View.INVISIBLE

            views.Every3m_select.visibility=View.INVISIBLE

            views.every6m_select.visibility=View.INVISIBLE

            views.yearly_select.visibility=View.INVISIBLE

            views.custom_layout_view.visibility=View.GONE

        }

        views.Daily_check.setOnClickListener {

            repeat_type=3

            views.selected_Type.text="Daily"

            ischeck_custom=true

            views.end_repeat_layout.visibility=View.VISIBLE

            views.never_select.visibility=View.INVISIBLE

            views.hourly_select.visibility=View.INVISIBLE

            views.Daily_select.visibility=View.VISIBLE

            views.weekly_select.visibility=View.INVISIBLE

            views.Fortnightly_select.visibility=View.INVISIBLE

            views.Monthly_select.visibility=View.INVISIBLE

            views.Every3m_select.visibility=View.INVISIBLE

            views.every6m_select.visibility=View.INVISIBLE

            views.yearly_select.visibility=View.INVISIBLE

            views.custom_layout_view.visibility=View.GONE

        }

        views.Weekly_check.setOnClickListener {

            repeat_type=4

            ischeck_custom=true

            views.selected_Type.text="Weekly"

            views.end_repeat_layout.visibility=View.VISIBLE

            views.never_select.visibility=View.INVISIBLE

            views.hourly_select.visibility=View.INVISIBLE

            views.Daily_select.visibility=View.INVISIBLE

            views.weekly_select.visibility=View.VISIBLE

            views.Fortnightly_select.visibility=View.INVISIBLE

            views.Monthly_select.visibility=View.INVISIBLE

            views.Every3m_select.visibility=View.INVISIBLE

            views.every6m_select.visibility=View.INVISIBLE

            views.yearly_select.visibility=View.INVISIBLE

            views.custom_layout_view.visibility=View.GONE

        }

        views.fortnightly_check.setOnClickListener {

            repeat_type=5

            views.selected_Type.text="Fortnightly"

            ischeck_custom=true

            views.end_repeat_layout.visibility=View.VISIBLE

            views.never_select.visibility=View.INVISIBLE

            views.hourly_select.visibility=View.INVISIBLE

            views.Daily_select.visibility=View.INVISIBLE

            views.weekly_select.visibility=View.INVISIBLE

            views.Fortnightly_select.visibility=View.VISIBLE

            views.Monthly_select.visibility=View.INVISIBLE

            views.Every3m_select.visibility=View.INVISIBLE

            views.every6m_select.visibility=View.INVISIBLE

            views.yearly_select.visibility=View.INVISIBLE

            views.custom_layout_view.visibility=View.GONE
        }

        views.Monthly_check.setOnClickListener {

            repeat_type=6

            views.selected_Type.text="Monthly"

            ischeck_custom=true

            views.end_repeat_layout.visibility=View.VISIBLE

            views.never_select.visibility=View.INVISIBLE

            views.hourly_select.visibility=View.INVISIBLE

            views.Daily_select.visibility=View.INVISIBLE

            views.weekly_select.visibility=View.INVISIBLE

            views.Fortnightly_select.visibility=View.INVISIBLE

            views.Monthly_select.visibility=View.VISIBLE

            views.Every3m_select.visibility=View.INVISIBLE

            views.every6m_select.visibility=View.INVISIBLE

            views.yearly_select.visibility=View.INVISIBLE

            views.custom_layout_view.visibility=View.GONE

        }

        views.every3m_check.setOnClickListener {

            repeat_type=7

            views.selected_Type.text="Every 3 Months"

            ischeck_custom=true

            views.end_repeat_layout.visibility=View.VISIBLE

            views.never_select.visibility=View.INVISIBLE

            views.hourly_select.visibility=View.INVISIBLE

            views.Daily_select.visibility=View.INVISIBLE

            views.weekly_select.visibility=View.INVISIBLE

            views.Fortnightly_select.visibility=View.INVISIBLE

            views.Monthly_select.visibility=View.INVISIBLE

            views.Every3m_select.visibility=View.VISIBLE

            views.every6m_select.visibility=View.INVISIBLE

            views.yearly_select.visibility=View.INVISIBLE

            views.custom_layout_view.visibility=View.GONE

        }

        views.every6m_check.setOnClickListener {

            repeat_type=8

            views.selected_Type.text="Every 6 Months"

            ischeck_custom=true

            views.end_repeat_layout.visibility=View.VISIBLE

            views.never_select.visibility=View.INVISIBLE

            views.hourly_select.visibility=View.INVISIBLE

            views.Daily_select.visibility=View.INVISIBLE

            views.weekly_select.visibility=View.INVISIBLE

            views.Fortnightly_select.visibility=View.INVISIBLE

            views.Monthly_select.visibility=View.INVISIBLE

            views.Every3m_select.visibility=View.INVISIBLE

            views.every6m_select.visibility=View.VISIBLE

            views.yearly_select.visibility=View.INVISIBLE

            views.custom_layout_view.visibility=View.GONE

        }

        views.yearly_check.setOnClickListener {

            repeat_type=9

            views.selected_Type.text="Yearly"

            ischeck_custom=true

            views.end_repeat_layout.visibility=View.VISIBLE

            views.never_select.visibility=View.INVISIBLE

            views.hourly_select.visibility=View.INVISIBLE

            views.Daily_select.visibility=View.INVISIBLE

            views.weekly_select.visibility=View.INVISIBLE

            views.Fortnightly_select.visibility=View.INVISIBLE

            views.Monthly_select.visibility=View.INVISIBLE

            views.Every3m_select.visibility=View.INVISIBLE

            views.every6m_select.visibility=View.INVISIBLE

            views.yearly_select.visibility=View.VISIBLE

            views.custom_layout_view.visibility=View.GONE

        }
        var isrepeat_check=true

        views.end_repeat_layout.setOnClickListener {


            if(isrepeat_check) {

                views.end_repeat_list_view_layout.visibility = View.VISIBLE

                isrepeat_check=false

            }
            else{

                views.end_repeat_list_view_layout.visibility = View.GONE

                isrepeat_check=true

            }

        }

        var no_end=true

        var is_select_date=true

        views.no_end_check.setOnClickListener {

            /*  if(no_end){

                  views.no_end_select.visibility=View.VISIBLE


                  views.end_repeat_select.visibility=View.INVISIBLE

                  views.get_end_date.visibility=View.GONE

                  no_end=false

                  is_select_date=false

              }
              else{

                  views.no_end_select.visibility=View.INVISIBLE

                  views.end_repeat_select.visibility=View.INVISIBLE

                  views.get_end_date.visibility=View.GONE

                  no_end=true

                  is_select_date=true

              }*/


            val cal = Calendar.getInstance()

            cal . setTime (Startdate)

            cal . add (Calendar.YEAR, 3)

            Enddate=cal.time

            views.no_end_select.visibility=View.VISIBLE

            views.end_repeat_select.visibility=View.INVISIBLE

            // views.get_end_date.visibility=View.GONE

            viewers!!.end_repeat_data.setText("")

        }

        views.end_repeat_check.setOnClickListener {

            /*
                        if(is_select_date){

                            views.no_end_select.visibility=View.INVISIBLE

                            views.end_repeat_select.visibility=View.VISIBLE

                            views.get_end_date.visibility=View.VISIBLE
                            is_select_date=true

                            no_end=true
                        }
                        else{
                            views.no_end_select.visibility=View.INVISIBLE

                            views.end_repeat_select.visibility=View.INVISIBLE

                            views.get_end_date.visibility=View.INVISIBLE

                            is_select_date=false

                            no_end=false
                        }
                        */

            views.no_end_select.visibility=View.INVISIBLE

            views.end_repeat_select.visibility=View.VISIBLE

            //    views.get_end_date.visibility=View.VISIBLE

            Untildate(views.context,"End Repeat")

        }


        views.custom_layout.setOnClickListener {

            views.end_repeat_layout.visibility=View.VISIBLE

            if(ischeck_custom){

                repeat_type=10

                views.selected_Type.text="Custom"

                views.never_select.visibility=View.INVISIBLE

                views.hourly_select.visibility=View.INVISIBLE

                views.Daily_select.visibility=View.INVISIBLE

                views.weekly_select.visibility=View.INVISIBLE

                views.Fortnightly_select.visibility=View.INVISIBLE

                views.Monthly_select.visibility=View.INVISIBLE

                views.Every3m_select.visibility=View.INVISIBLE

                views.every6m_select.visibility=View.INVISIBLE

                views.yearly_select.visibility=View.INVISIBLE

                views.custom_layout_view.visibility=View.VISIBLE

                ischeck_custom=false
            }
            else{

                repeat_type=1

                views.selected_Type.text=""

                views.never_select.visibility=View.VISIBLE

                views.custom_layout_view.visibility=View.GONE

                ischeck_custom=true
            }

        }

        val name  =views.findViewById<NumberPicker>(R.id.custom_type_select)

        val values = arrayOf( "Daily", "Weekly", "Monthly", "Yearly")

        name.minValue = 0

        name.maxValue = values.size - 1

        name.displayedValues = values

        name.wrapSelectorWheel = true

        name.setOnScrollListener { numberPicker, i2 ->

            Log.e("selected","$i2")

            if(i2==0){


                var number=name.value

                Log.e("number","....$number")


               /* if(number==0){

                    frequency_type_id=0

                    Toast.makeText(views.context,"Please select one", Toast.LENGTH_LONG).show()

                    views.frequency_Type.text=values[number]

                }
                else*/
                if(number==0){


                    if(dialog!=null){

                        dialog!!.dismiss()
                    }

                    views.frequency_Type.text=values[number]

                    dialog_days_picker(views.context,1)

                }
                else if(number==1){

                    if(dialog!=null){

                        dialog!!.dismiss()
                    }

                 //   frequency_type_id=2

                    views.frequency_Type.text=values[number]

                    dialog_week_picker(views.context)


                }
                else if(number==2){

                    if(dialog!=null){

                        dialog!!.dismiss()
                    }

                //    frequency_type_id=3

                    views.frequency_Type.text=values[number]

                    dialog_days_picker(views.context,2)

                }
                else if(number==3){

                    if(dialog!=null){

                        dialog!!.dismiss()
                    }

                  //  frequency_type_id=4

                    views.frequency_Type.text=values[number]

                    dialog_days_picker(views.context,3)

                }

            }

/*
            if(i2==0){
                Toast.makeText(views.context,"Please select one",Toast.LENGTH_LONG).show()

                views.frequency_Type.text=values[i2]

            }
            else if(i2==1){

                views.frequency_Type.text=values[i2]

                dialog_days_picker(views.context,1)

            }
            else if(i2==2){
                views.frequency_Type.text=values[i2]

            }
            else if(i2==3){
                views.frequency_Type.text=values[i2]

                dialog_days_picker(views.context,2)

            }
            else if(i2==4){

                views.frequency_Type.text=values[i2]

                dialog_days_picker(views.context,3)

            }*/

        }

        var work_ischeck=false

        views.save_data_update.setOnClickListener {

            Log.e("CLicked save","waesrdtfgyhujiko")

            val curent=Date()

            val formatter = SimpleDateFormat("dd/MM/yyyy")

            var date=formatter.format(curent)

            val selective= formatter.parse(date)

            Log.e("qwertyu","$selective $Startdate")

            var isfalse=false

/*

            if(reminder_time==null){

              //  Toast.makeText(views.context,"Please set reminder",Toast.LENGTH_LONG).show()
            }
            else {


                if (repeat_type == 1 && reminder_time!! < Date()) {

                    if (Startdate == selective) {

                        isfalse = true

                    }


                }
            }
*/

            when{

                views.medicine_name.text.trim().isNullOrEmpty()-> Toast.makeText(views.context,"Please enter task name", Toast.LENGTH_LONG).show()

                reminder_time==null-> Toast.makeText(views.context,"Please set reminder", Toast.LENGTH_LONG).show()

                (repeat_type==1 && (reminder_time!! < Date()))-> Toast.makeText(views.context,"Reminder Time is invalid",Toast.LENGTH_LONG).show()

                else ->{
                    AsyncTask.execute {
                        var userId by stringPref("userId", null)
                        var petid by stringPref("petid", null)
                        var user = userId?.toInt()
                        var petId = petid?.toLong()
                        try {
                            val callDetails = splootDB!!.petMasterDao()

                            val insert_data = madicineType(
                                userId = userId,
                                petId = petid,
                                task_name = views.medicine_name.text.toString().trim(),
                                start_date = Startdate,
                                end_date = Enddate,
                                repeat_type = repeat_type,
                                frequency_type_id = frequency_type_id,
                                every_frequency = every_frequency,
                                selective_week = selective_week,
                                reminder_time = reminder_time,
                                cat_type = cat_type,
                                active = 1
                            )

                            val fun_insert = callDetails.cat_insert(insert_data)

                            val viewall_date=callDetails.getAll_cat(userId!!,petid!!)

                            Log.e("display all data","$viewall_date")

                            val last_data=callDetails.getlast_one()

                            Log.e("last_data","$last_data")

                            val RQS_1=last_data.allTypeId

                            val calNow = Calendar . getInstance ();

                            val calSet:Calendar = calNow . clone () as Calendar;

                    /*        calSet.set(Calendar.DATE, reminder_time!!.date);

                            calSet.set(Calendar.MONTH, reminder_time!!.month);

                            calSet.set(Calendar.YEAR, reminder_time!!.year);*/

                            calSet.set(Calendar.HOUR_OF_DAY, reminder_time!!.hours);

                            calSet.set(Calendar.MINUTE, reminder_time!!.minutes);

                            calSet.set(Calendar.SECOND, 0);

                            calSet.set(Calendar.MILLISECOND, 0);

                            if (calSet.compareTo(calNow) <= 0) {
                                // Today Set time passed, count to tomorrow

                                calSet.add(Calendar.DATE, 1);

                            }

                            setAlarm(calSet,RQS_1!!.toInt());

                            val mContext = activity

                            val fm = mContext?.supportFragmentManager

                            val transaction = fm?.beginTransaction()

                            transaction?.replace(R.id.task_frame,taskfragmentActivity.newInstance(viewdata!!, cont!!))

                            transaction?.commit()


                        } catch (e: Exception) {
                            val s = e.message
                            Log.e("Error", s)
                        }
                    }
                    Toast.makeText(views.context,"Saved successfully", Toast.LENGTH_LONG).show()
                }

            }

        }

        return views
    }

/*


    private fun setAlarm(
        targetCal: Calendar,
        id: Int
    ) {

        val intent = Intent(viewers!!.context, AlarmReceiver::class.java)

        intent.putExtra("name", "$id")

        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

        var DAY = 86400000L

        var millisec = System.currentTimeMillis().toInt()

        val pendingIntent = PendingIntent.getBroadcast(activity, millisec, intent, 0);

        val alarmManager: AlarmManager =
            viewers!!.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager;

        if (repeat_type==1){

            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)

        }
        else if(repeat_type==2){

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),AlarmManager.INTERVAL_HOUR, pendingIntent);

        }
        else if(repeat_type==3){

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

        }

        else if(repeat_type==4){

            val day=DAY*7

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),day, pendingIntent);

        }
        else if(repeat_type==5){

            val day=DAY*14

        }
        else if(repeat_type==6){

            val date=getDuration(targetCal)

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),date, pendingIntent)


        }
        else if(repeat_type==7){

            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)


        }
        else if(repeat_type==8){

            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)


        }
        else if(repeat_type==9){

            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)


        }
        else if(repeat_type==10){


            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)


        }

     */
/*   val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT < Build.VERSION_CODES.KITKAT) {

            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)
        }
        else if (Build.VERSION_CODES.KITKAT <= SDK_INT && SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                targetCal.getTimeInMillis(),
                pendingIntent
            )
        }
        else if (SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)
        }*//*


        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

    }


*/


    private fun setAlarm(
        targetCal: Calendar,
        id: Int
    ) {

        val intent = Intent(viewers!!.context, AlarmReceiver::class.java)

        Log.e("alaram data", "${targetCal.time}")

        intent.putExtra("name", "$id")

        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

        var DAY = 86400000L

        var millisec = System.currentTimeMillis().toInt()


        val pendingIntent = PendingIntent.getBroadcast(activity, millisec, intent, 0);

        val alarmManager: AlarmManager =
            activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager;



        if (repeat_type == 1) {


            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val output = formatter.format(Date())

            val out = formatter.parse(output)



            if (Startdate!!.compareTo(out) < 0) {

                Log.e("alaram", "check ${out}")

                //  Toast.makeText(this.context,"")


            } else {


                val valide = Startdate!!.time - Date().time

                Log.e("alaram validate", "$valide")

                val data_responce = (valide / (1000 * 3600 * 24)).toInt()

                var tar = data_responce

                Log.e("alaram", "$tar")

                if (valide < 0) {
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        targetCal.getTimeInMillis(),
                        pendingIntent
                    )
                } else {

                    val time1 = (tar + 1) * DAY

                    targetCal.add(Calendar.DATE, (tar))

                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        targetCal.getTimeInMillis(),
                        pendingIntent
                    )


                }

                Log.e("talat", "" + tar)

            }

        } else if (repeat_type == 2) {


            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val output = formatter.format(Date())

            val out = formatter.parse(output)



            if (Startdate!!.compareTo(out) < 0) {

                Log.e("alaram", "check ${out}")

                //  Toast.makeText(this.context,"")


                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                    AlarmManager.INTERVAL_HOUR, pendingIntent
                )


            } else {


                val valide = Startdate!!.time - Date().time

                Log.e("alaram validate", "$valide")

                val data_responce = (valide / (1000 * 3600 * 24)).toInt()

                var tar = data_responce

                Log.e("alaram", "$tar")

                if (valide < 0) {
                    //alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)


                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                        AlarmManager.INTERVAL_HOUR, pendingIntent
                    )

                } else {

                    val time1 = (tar + 1) * DAY

                    targetCal.add(Calendar.DATE, (tar))

                    //  alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)


                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                        AlarmManager.INTERVAL_HOUR, pendingIntent
                    )


                }

                Log.e("talat", "" + tar)

            }


            /*    val formatter = SimpleDateFormat("dd/MM/yyyy")

                val output = formatter.format(Date())

                val out=formatter.parse(output)



                if(Startdate!!.compareTo(out)<0 )
                {

                    Log.e("alaram","check ${out}")

                    //  Toast.makeText(this.context,"")


                }
                else {


                    val valide=  Startdate!!.time  - Date().time

                    Log.e("alaram validate","$valide")

                    val data_responce = (valide / (1000 * 3600 * 24)).toInt()

                    var tar=data_responce

                    Log.e("alaram","$tar")

                    if(valide<0)
                    {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent)


                        alarmManager.setInexactRepeating(
                            AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                            AlarmManager.INTERVAL_HOUR, pendingIntent);

                    }
                    else {

                        val time1=(tar+1)*DAY

                           //      alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis()+time1, pendingIntent)


                        targetCal.add(Calendar.DATE,(tar))

                        alarmManager.setInexactRepeating(
                            AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                            AlarmManager.INTERVAL_HOUR, pendingIntent);


                    }

                    Log.e("talat",""+tar)

                }*/

/*
            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val output = formatter.format(Date())

            val out=formatter.parse(output)

            if(Startdate!!.compareTo(out)<0 ) {

                Log.e("alaram","check ${out}")

            }
            else {


                val start = formatter.format(Startdate)

                val str = formatter.parse(start)

                val valide = str.time - out.time

                Log.e("alaram validate", "$valide")

                val data_responce = (valide / (1000 * 3600 * 24)).toInt()

                var tar = data_responce

                Log.e("alaram", "$tar")

                if (tar == 0) {

                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                        AlarmManager.INTERVAL_HOUR, pendingIntent
                    )

                } else if (tar < 0) {


                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                        AlarmManager.INTERVAL_HOUR, pendingIntent
                    )


                } else {

                    val time1 = (tar + 1) * DAY


                    Log.e("alarm", "dsa $time1 $valide")

                    targetCal.add(Calendar.DATE, (tar))

                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent
                    );


                }

            }*/


        } else {


            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                targetCal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            );


            /*

               val formatter = SimpleDateFormat("dd/MM/yyyy")

               val output = formatter.format(Date())

               val out=formatter.parse(output)

               val start=formatter.format(Startdate)

               val str=formatter.parse(start)

                   val valide=  str.time  - out.time

                   Log.e("alaram validate","$valide")

                   val data_responce = (valide / (1000 * 3600 * 24)).toInt()

                   var tar=data_responce

                   Log.e("alaram","$tar")


               if(tar == 0){

                   alarmManager.setInexactRepeating(
                       AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                       AlarmManager.INTERVAL_DAY, pendingIntent)

               }

                   else if(tar<0)
                   {

                       val time1=(tar-1)*DAY

                       Log.e("alarm","dsa $time1 $valide")

                       targetCal.add(Calendar.DATE,(tar))

                       alarmManager.setInexactRepeating(
                           AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                           AlarmManager.INTERVAL_DAY, pendingIntent);

                   }
                   else {

                       val time1=(tar+1)*DAY

                       //      alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis()+time1, pendingIntent)

                   Log.e("alarm","dsa $time1 $valide")

                   targetCal.add(Calendar.DATE,(tar))

                       alarmManager.setInexactRepeating(
                           AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                           AlarmManager.INTERVAL_DAY, pendingIntent);


                   }
   */
        }
    }

    private fun getDuration(targetCal: Calendar): Long {
        // get todays date
        val cal = targetCal
        // get current month
        var currentMonth = cal.get(Calendar.MONTH)

        // move month ahead
        currentMonth++
        // check if has not exceeded threshold of december

        if (currentMonth > Calendar.DECEMBER) {
            // alright, reset month to jan and forward year by 1 e.g fro 2013 to 2014
            currentMonth = Calendar.JANUARY
            // Move year ahead as well
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1)
        }

        // reset calendar to next month
        cal.set(Calendar.MONTH, currentMonth)
        // get the maximum possible days in this month
        val maximumDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        // set the calendar to maximum day (e.g in case of fEB 28th, or leap 29th)
        cal.set(Calendar.DAY_OF_MONTH, maximumDay)

        return cal.timeInMillis // this is what you set as trigger point time i.e one month after

    }



    fun dialog_days_picker(commandAdapter: Context, type:Int) {
        dialog = Dialog(commandAdapter)
        Log.e("diloge", "------------------------------------------>")
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.select_frequency_dialog)

        val name =dialog!!.findViewById<NumberPicker>(R.id.dialog_date_picker)

        name.minValue=1

        name.maxValue=999

        var seleted_data=1

        if(type==1) {

            dialog!!.picker_header.text="Every Day"

            dialog!!.type_pickers.text="Day"


        }
        else if(type==2){

            dialog!!.picker_header.text="Every Month"

            dialog!!.type_pickers.text="Month"


        }
        else if(type==3){

            dialog!!.picker_header.text="Every Year"

            dialog!!.type_pickers.text="Year"

        }


        dialog!!.frequency_cancel.setOnClickListener {

            dialog!!.dismiss()
        }

        name.setOnValueChangedListener { _, _, i2 ->

            seleted_data=i2

        }


        dialog!!.frequency_ok.setOnClickListener {

            every_frequency=seleted_data

//wieuigbs

            if(type==1) {

                frequency_type_id=1


                if(seleted_data==1){

                    viewers!!.custom_data_view.text="$seleted_data Day"

                }
                else{

                    viewers!!.custom_data_view.text="$seleted_data Day"
                }



            }
            else if(type==2){

                frequency_type_id=3

                if(seleted_data==1){

                    viewers!!.custom_data_view.text="$seleted_data Month"

                }
                else{

                    viewers!!.custom_data_view.text="$seleted_data Months"

                }



            }
            else if(type==3){

                frequency_type_id=4

                if(seleted_data==1){

                    viewers!!.custom_data_view.text="$seleted_data Year"

                }
                else{

                    viewers!!.custom_data_view.text="$seleted_data Years"

                }
            }

            dialog!!.dismiss()

            /*   if(type==0){

                   Log.e("Selected","type 1 $seleted_data")

                   dialog.dismiss()

               }
               else if(type==1){

                   Log.e("Selected","type 2 $seleted_data")

                   dialog.dismiss()

               }
               else if(type==2){

                   Log.e("Selected","type 3 $seleted_data")

                   dialog.dismiss()
               }*/


        }

        dialog!!.show()

    }

    fun dialog_week_picker(commandAdapter: Context) {

        dialog = Dialog(commandAdapter)

        Log.e("diloge", "------------------------------------------>")

        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog!!.setCancelable(false)

        dialog!!.setContentView(R.layout.week_dialog_picker)

        val name =dialog!!.findViewById<NumberPicker>(R.id.dialog_week_picker)

        name.minValue=1

        name.maxValue=999

        var seleted_data=1

        var weekdayselect:String?=""

        dialog!!.week_picker_header.setText("Every Week")


        dialog!!.week_Monday?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Monday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Monday", "")

            }
        }
        dialog!!.week_tueasdy?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Tuesday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Tuesday", "")

            }
        }
        dialog!!.week_wednesday?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Wednesday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Wednesday", "")

            }
        }
        dialog!!.week_thuesday?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Thursday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Thursday", "")

            }
        }
        dialog!!.week_friday?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Friday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Friday", "")

            }
        }
        dialog!!.week_saterday?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Saturday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Saturday", "")

            }
        }
        dialog!!.week_sunday?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Sunday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Sunday", "")

            }
        }



        dialog!!.week_frequency_cancel.setOnClickListener {

            dialog!!.dismiss()
        }

        name.setOnValueChangedListener { _, _, i2 ->


            if(i2==1){

                dialog!!.week_select.text="week"

            }
            else{

                dialog!!.week_select.text="weeks"

            }


            seleted_data=i2

        }


        dialog!!.week_frequency_ok.setOnClickListener {

            frequency_type_id=2

            every_frequency=seleted_data

            selective_week=weekdayselect

            Log.e("work","Week day $seleted_data , $weekdayselect")

            if(seleted_data==1){

                viewers!!.custom_data_view.text="$seleted_data Week"

            }
            else{

                viewers!!.custom_data_view.text="$seleted_data Weeks"


            }



            dialog!!.dismiss()


        }

        dialog!!.show()

    }

    fun Untildate(commandAdapter: Context, datas: String): String? {


        dialog = Dialog(commandAdapter)
        Log.e("diloge","------------------------------------------>")
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.calender)
        var date=""

        var date_1: Date?=null

        val dateget =dialog!!.findViewById<CalendarView>(R.id.getdate)

        dialog!!.headertext.text=datas

        dateget.minDate=Startdate!!.time

        dateget?.setOnDateChangeListener { view, year, month, dayOfMonth ->

            Log.e("Data","...............${Date(view.date)}")

            date = "" + dayOfMonth + "/" + (month + 1) + "/" + year

            val simpledate= SimpleDateFormat("")

            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val output = formatter.parse(date)

            val formatter1 = SimpleDateFormat("dd/MM/yyyy")

            val output1=formatter1.format(output)

            date=output1

            date_1=output

        }
        dialog!!.calendercan.setOnClickListener {

            Enddate=null

            viewers!!.end_repeat_select.visibility=View.INVISIBLE

            viewers!!.end_repeat_data.setText("")

            dialog!!.dismiss()
        }
        dialog!!.calenderokbtn.setOnClickListener {

            Enddate=date_1

            viewers!!.end_repeat_data.setText(date)

            dialog!!.dismiss()
        }

        dialog!!.show()

        return null

    }

}