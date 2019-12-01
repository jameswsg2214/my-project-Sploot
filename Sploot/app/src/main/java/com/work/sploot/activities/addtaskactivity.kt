package com.work.sploot.activities

import android.app.Dialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import com.work.sploot.Entity.alarmdata
import com.work.sploot.Entity.taskdata
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import kotlinx.android.synthetic.main.addtaskfragment.view.*
import kotlinx.android.synthetic.main.calender.*
import kotlinx.android.synthetic.main.dosage.*
import kotlinx.android.synthetic.main.gettime.*
import kotlinx.android.synthetic.main.vaccination.view.*
import java.text.SimpleDateFormat
import java.util.*

class addtaskactivity:Fragment() {
    private var splootDB: SplootAppDB? = null
    var viewS:View?=null

    var startdate:Date?=null
    var enddate:Date?=null
    var xtimeaday:String?=null
    var xday:String?=null
    var dosagedata:String?=null
    var hoursreminderdata:String?=null
    var firstintake:String?=null
    var lastintake:String?=null
    var active:String?=null
    var paused:String?=null
    var cycle:String?=null
    var weekdayselect=""

    var recyclerView:RecyclerView?=null

    companion object {

        var viewdata: View? =null

        var cont:FragmentActivity?=null

        fun newInstance(view: View?, mContext: FragmentActivity?
        ): addtaskactivity {
            viewdata=view
            cont=mContext
            return addtaskactivity()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views=inflater.inflate(R.layout.addtaskfragment, container, false)
        splootDB = SplootAppDB.getInstance(views.context)
        viewS=views

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        val dateInString = sdf.format(Date())

        val formatter = SimpleDateFormat("dd/MM/yyyy")

        val date = formatter.parse(dateInString)

        startdate=date

        views.start_date_select.text=dateInString



        recyclerView=views.findViewById(R.id.reminder_recycler)
        views.save_data.setOnClickListener {
            when{
                views.enter_task_name.text.trim().isNullOrEmpty()-> views.enter_task_name.error=" Task name Field is Empty"
                startdate==null-> Toast.makeText(views.context,"please Select start data",Toast.LENGTH_LONG).show()
                enddate==null-> Toast.makeText(views.context,"Please Select last date",Toast.LENGTH_LONG).show()
                else->{
                    taskinsert(views.enter_task_name.text.toString(),startdate)
                    views.enter_task_name.isFocusable = false
                    val mContext = activity
                    val fm = mContext?.supportFragmentManager
                    val transaction = fm?.beginTransaction()
                    transaction?.replace(R.id.task_frame,taskfragmentActivity.newInstance(viewdata!!, cont!!))
                    transaction?.commit()
                }
            }


        }

        views.alaram_click_layout.setOnClickListener {
           // remindertime(views.context,"Set Reminder")

            var datav= SingleDateAndTimePickerDialog.Builder(views.context)
                .bottomSheet()
                .curved()
                .displayMinutes(true)
                .displayHours(true)
                .displayDays(false)
                .displayMonth(false)
                .displayYears(false)
                .title("Set Reminder")
                .displayListener(SingleDateAndTimePickerDialog.DisplayListener { picker ->

                }).listener { date: Date? ->

                    Log.e("date","$date.........")

                    reminderInsert(date!!)

                }.display()
        }


        var isckick_true=true


        views.duration_click_layout.setOnClickListener {


            val inputMethodManager =
                views.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

            if(isckick_true){

                views.duration_layout.visibility=View.VISIBLE
                views.frequency_layout.visibility=View.GONE

                isckick_true=false

            }
            else{

                views.duration_layout.visibility=View.GONE
                views.frequency_layout.visibility=View.GONE
                isckick_true=true

            }



        }


        var isclick_freqency=true



        views.frequency_click_layout.setOnClickListener {
            val inputMethodManager =
                views.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

            if(isclick_freqency){

                views.frequency_layout.visibility=View.VISIBLE

                views.duration_layout.visibility=View.GONE

                isclick_freqency=false

            }
            else{

                views.frequency_layout.visibility=View.GONE

                views.duration_layout.visibility=View.GONE

                isclick_freqency=true

            }

        }

        views.start_day_layout.setOnClickListener {
            getdate(views.context,"Start date")
        }




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




        views.radio_group1.setOnClickListener {

            val cal = Calendar.getInstance()

            cal . setTime (startdate)

            cal . add (Calendar.YEAR, 1)

            enddate=cal.time

            views?.duration_data_format?.text = "No end data"

            views?.duration_data?.text = "No end"

        }

        views.radio_group2.setOnClickListener {
            Untildate(views.context,"Until Date")
        }
        views.radio_group3.setOnClickListener {
            dialogdayspicker(views.context,"For X days",4)


        }


        views.radio_Button1.setOnClickListener {
        //    views.multi_process_view.visibility=View.GONE

            views.check_box_layout.visibility=View.GONE

            views.xy_view.visibility=View.GONE

            dialogtimescountpicker(views.context,"Daily,X times a day")

            views.task_freq_data_layout.visibility=View.VISIBLE

        }
//        views.radio_Button2.setOnClickListener {
//            views.multi_process_view.visibility=View.VISIBLE
//            views.check_box_layout.visibility=View.GONE
//            views.xy_view.visibility=View.GONE
//        }
        views.radio_Button3.setOnClickListener {
          //  views.multi_process_view.visibility=View.GONE

            views.check_box_layout.visibility=View.GONE

            views.xy_view.visibility=View.GONE

            views.task_freq_data_layout.visibility=View.VISIBLE

            dialogdayspicker(views.context,"Every X days",0)


        }
        views.radio_Button5.setOnClickListener {
            views.xy_view.visibility=View.VISIBLE
//            views.multi_process_view.visibility=View.GONE

            views.check_box_layout  .visibility=View.GONE

            views.task_freq_data_layout.visibility=View.GONE

        }
        views.radio_Button4.setOnClickListener {
            views.xy_view.visibility=View.GONE

//            views.multi_process_view.visibility=View.GONE

            views.check_box_layout.visibility=View.VISIBLE

            views.task_freq_data_layout.visibility=View.GONE
        }


        views.day_active_layout.setOnClickListener {
            dialogdayspicker(views.context,"DaysActive",1)

        }
        views.Day_paused_layout.setOnClickListener {
            dialogdayspicker(views.context,"DaysPaused",2)
        }
        views.cycle_day_layout.setOnClickListener {
            dialogdayspicker(views.context,"Today is cycle day",3)
        }
        views.Monday_?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Monday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Monday", "")

            }
        }
        views.tueasdy_?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Tuesday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Tuesday", "")

            }
        }
        views.wednesday_?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Wednesday"

            }
            else{
                weekdayselect = weekdayselect?.replace("Wednesday", "")

            }
        }
        views.thuesday_?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Thursday"
            }
            else{
                weekdayselect = weekdayselect?.replace("Thursday", "")
            }
        }
        views.friday_?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Friday"
            }
            else{
                weekdayselect = weekdayselect?.replace("Friday", "")
            }
        }
        views.saterday_?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Saturday"
            }
            else{
                weekdayselect = weekdayselect?.replace("Saturday", "")
            }
        }
        views.sunday_?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                weekdayselect+="Sunday"
            }
            else{
                weekdayselect = weekdayselect?.replace("Sunday", "")

            }
        }
        return views
    }

    fun getdate(commandAdapter: Context, datas: String): String? {
        val dialog = Dialog(commandAdapter)
        Log.e("diloge","------------------------------------------>")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.calender)

        var date=""

        var select_date:Date?=null

        val dateget =dialog.findViewById<CalendarView>(R.id.getdate)

        dialog.headertext.text=datas

        dateget?.setOnDateChangeListener { view, year, month, dayOfMonth ->


            date = "" + dayOfMonth + "/" + (month + 1) + "/" + year

            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val output = formatter.parse(date)

            select_date=output

        }
        dialog.calendercan.setOnClickListener {
            dialog.dismiss()
        }
        dialog.calenderokbtn.setOnClickListener {

            viewS?.start_date_select?.text = date

            startdate=select_date

            dialog.dismiss()
        }
        dialog.show()
        return null
    }


    fun Untildate(commandAdapter: Context, datas: String): String? {
        val dialog = Dialog(commandAdapter)
        Log.e("diloge","------------------------------------------>")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.calender)
        var date=""

        var select_date:Date?=null
        val dateget =dialog.findViewById<CalendarView>(R.id.getdate)
        dialog.headertext.text=datas
        dateget?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = "" + dayOfMonth + "/" + (month + 1) + "/" + year


            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val output = formatter.parse(date)

            select_date=output
        }
        dialog.calendercan.setOnClickListener {

            dialog.dismiss()
        }
        dialog.calenderokbtn.setOnClickListener {

            viewS?.duration_data_format?.setText("Untill")

            viewS?.duration_data?.setText(date)

            enddate=select_date

            Log.e("EndDate","$enddate")

            dialog.dismiss()
        }
        dialog.show()
        return null
    }


    fun dialogtimescountpicker(commandAdapter: Context, datas: String): String? {
        val dialog = Dialog(commandAdapter)
        Log.e("diloge","------------------------------------------>")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dosage)
        var data="dabsbdjshjhsa"
        val name =dialog.findViewById<NumberPicker>(R.id.numberpicker)
        val header=dialog.findViewById<TextView>(R.id.headertextfordata)
        header.text = datas
        name.minValue=0
        name.maxValue=10
        dialog.calendercancel.setOnClickListener {
            viewS?.task_freq_format?.text = "Daily X times a day"
            viewS?.task_freq_data?.text = "$xtimeaday Times"
            dialog.dismiss()
        }

        name.setOnValueChangedListener { numberPicker, i, i2 ->
            Log.e("Picker","$i2")

            data= i2.toString()
        }
        dialog.calenderok.setOnClickListener {
            if(data==null)
            {
                xtimeaday="1"

                viewS?.task_freq_format?.text = "Daily X times a day"
                viewS?.task_freq_data?.text = "$xtimeaday Times"

            }
            else{
                xtimeaday=data

                viewS?.task_freq_format?.text = "Daily X times a day"
                viewS?.task_freq_data?.text = xtimeaday
            }
            dialog.dismiss()
        }

        dialog.show()
        return null
    }

    fun dialogdayspicker(commandAdapter: Context, datas: String,type:Int): String? {
        val dialog = Dialog(commandAdapter)
        Log.e("diloge","------------------------------------------>")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dosage)
        var data="1"
        val name =dialog.findViewById<NumberPicker>(R.id.numberpicker)
        val header=dialog.findViewById<TextView>(R.id.headertextfordata)
        header.text = datas

        val values = arrayOf("1 Days", "2 Days", "3 Days", "4 Days", "5 Days","6 Days","7 Days","8 Days","9 Days","10 Days","11 Days","12 Days","13 Days","14 Days","15 Days")

        name.minValue = 0
        name.maxValue = values.size - 1
        name.displayedValues = values
        name.wrapSelectorWheel = true

        dialog.calendercancel.setOnClickListener {
            xday=data
            if(type==0) {
                viewS?.task_freq_format?.text = "Every X days"
            }

            //viewS?.task_freq_data?.text = xday
            dialog.dismiss()
        }

        dialog.calenderok.setOnClickListener {
            if (type==0){
                xday=data
                viewS?.task_freq_format?.text = "Every X days"
                viewS?.task_freq_data?.text = xday
            }
            else if(type==1){
                active=data
                viewS?.day_activevery_text?.setText(data)
            }
            else if(type==2){
                paused=data
                viewS?.Day_paused_text?.setText(data)
            }
            else if(type==3){
                cycle=data
                viewS?.today_cycle_text?.setText(data)
            }
            else if(type==4){

                //viewS?.todaycycletext?.setText(data_view)

                val cal = Calendar.getInstance()

                cal . setTime (startdate)

                cal . add (Calendar.DAY_OF_YEAR, data.toInt())

                enddate=cal.time

              //  viewS?.durationdataformat?.setText("For X days")

              //  viewS?.durationdata?.setText(data_view)

                //Log.e("Startdate","$Startdate")

                //Startdate
            }
            dialog.dismiss()

        }
        name.setOnValueChangedListener { _, _, i2 ->
            Log.e("Picker","hours"+(i2+1))
            data=(i2+1).toString()
        }
        dialog.show()
        return null
    }
    fun dialogtabletspicker(commandAdapter: Context, datas: String): String? {
        val dialog = Dialog(commandAdapter)
        Log.e("diloge","------------------------------------------>")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dosage)
        var data="1"
        val name =dialog.findViewById<NumberPicker>(R.id.numberpicker)
        val header=dialog.findViewById<TextView>(R.id.headertextfordata)
        header.text = datas
        header.text = datas

        val values = arrayOf("1 Tablet", "2 Tablet (s)", "3 Tablet (s)", "4 Tablet (s)", "5 Tablet (s)","6 Tablet (s)","7 Tablet (s)","8 Tablet (s)","9 Tablet (s)","10 Tablet (s)")
        name.minValue = 0
        name.maxValue = values.size - 1
        name.displayedValues = values
        name.wrapSelectorWheel = true

        dialog.calendercancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.calenderok.setOnClickListener {
            dosagedata=data
            dialog.dismiss()

        }
        name.setOnValueChangedListener { _, _, i2 ->
            Log.e("Picker","hours"+(i2+1))
            data=(i2+1).toString()
          //  viewS?.dosage_text?.setText(data)
        }
        dialog.show()
        return null
    }
    fun dialoghourreminderspicker(commandAdapter: Context, datas: String): String? {
        val dialog = Dialog(commandAdapter)
        Log.e("diloge","------------------------------------------>")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dosage)
        var data="1"
        val name =dialog.findViewById<NumberPicker>(R.id.numberpicker)
        val header=dialog.findViewById<TextView>(R.id.headertextfordata)
        header.text = datas

        val values = arrayOf("1 hours", "2 hours", "3 hours", "4 hours", "5 hours","6 hours","7 hours","8 hours","9 hours","10 hours","11 hours","12 hours")

        name.minValue = 0
        name.maxValue = values.size - 1
        name.displayedValues = values
        name.wrapSelectorWheel = true

        dialog.calendercancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.calenderok.setOnClickListener {
            hoursreminderdata=data
            dialog.dismiss()

        }
        name.setOnValueChangedListener { _, _, i2 ->
            Log.e("Picker","hours"+(i2+1))
            data=(i2+1).toString()
        //    viewS?.reminder_every_text?.setText(data)
        }
        dialog.show()
        return null
    }

    private fun taskinsert(task_name: String, startdate: Date?) {
        Log.e("data","$task_name")
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var user= userId?.toInt()
            var petId= petid?.toLong()
            try {
                val callDetails = splootDB!!.petMasterDao()
                    val insetdata= taskdata(
                        userId = userId,
                        petId = petid,
                        task_name = task_name,
                        startdate = startdate,
                        enddate = enddate,
                        active = "1"
                    )
                    val insert=callDetails.taskinsertAll(insetdata)
                    val viewdata=callDetails.getAlltask()


                val getdata=callDetails.getdataalarm(userId!!, petid!!,"2")

                Log.e("size","${getdata.size}")

                for(i in 0 ..getdata.size-1) {
                    var update = alarmdata(
                        alarmId =getdata[i].alarmId,
                        userId =getdata[i].userId,
                        petId = getdata[i].petId,
                        startdate = getdata[i].startdate,
                        endate = getdata[i].endate,
                        time = getdata[i].time,
                        notified_data = getdata[i].notified_data,
                        reminder_type = getdata[i].reminder_type,
                        active = "1"
                    )
                    var updatdata=callDetails.updatealarmdata(update)


                }

                var getall=callDetails.getalarmdata()

                Log.e("all","Alaram data $getall")


                    Log.e("responce","no data found $viewdata")

            } catch (e: Exception) {
                val s = e.message
                Log.e("Error",s)
            }
        }
    }



    fun remindertime(commandAdapter: Context, datas: String): String? {
        val dialog = Dialog(commandAdapter)
        Log.e("diloge","------------------------------------------>")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.gettime)
        val header=dialog.findViewById<TextView>(R.id.headertextfortime)
        header.text = datas
        var data=""
        var day="am"
        var hours=0

        var hor=""
        var min=""

        var check=true

        dialog.timecancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.timeok.setOnClickListener {

            if(check){

            }

            //reminderInsert(data)
            dialog.dismiss()
        }
        dialog.gettime.setOnTimeChangedListener { timePicker, hour, minute ->

            check=false
            if(hour>12){
                day="pm"
                hours =hour- 12
            } else{
                hours=hour
                day="am"

            }
            if(hours<10)
            {
                hor="0"+hours
            }else
            {
                hor=""+hours
            }

            if(minute<10)
            {
                min  ="0"+minute
            }else
            {
                min=""+minute
            }
            data="$hor:$min $day"
            Log.e("time", "Hour: "+ hours + " Minute : "+ minute+"day " +day)
        }
        dialog.show()
        return null
    }
    private fun reminderInsert(data: Date) {
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var Id= id?.toLong()
            try {
                val callDetails = splootDB!!.petMasterDao()
                Log.e("check data","statedatre = $id,petid=$petid,userid:$data")

                val insertdata= alarmdata(
                    userId = userId,
                    petId = petid,
                    startdate = startdate,
                    endate = enddate,
                    time =  data,
                    active = "2"
                )
                val insert_function=callDetails.insertalaramdata(insertdata)
                val getdata=callDetails.getdataalarm(userId!!, petid!!,"2")
                recyclerView?.post(Runnable {
                    recyclerView?.layoutManager = LinearLayoutManager(viewS?.context)

                 //   recyclerView?.layoutManager = GridLayoutManager(viewS?.context,2)
                    val adapter = CustomAdapter(getdata)
                    recyclerView?.adapter = adapter
                })


                Log.e("Reminder table data","$getdata")
            } catch (e: Exception) {
                val s = e.message;
                Log.e("Errors",s)
                // alarmid="0123"
            }
        }
    }
}