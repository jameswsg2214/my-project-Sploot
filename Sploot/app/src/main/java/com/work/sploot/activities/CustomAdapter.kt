package com.work.sploot.activities

import android.app.Dialog
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import com.work.sploot.Entity.alarmdata
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import kotlinx.android.synthetic.main.gettime.*
import kotlinx.android.synthetic.main.reminderview.view.*
import java.util.*

class CustomAdapter(var userList: List<alarmdata>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var viewesdata:View?=null
    private var splootDB: SplootAppDB? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.reminderview, parent, false)
        viewesdata=v
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])

        splootDB = SplootAppDB.getInstance(viewesdata!!.context)
        holder.itemView.time.setOnClickListener {

            var datav= SingleDateAndTimePickerDialog.Builder(viewesdata!!.context)
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

               //     reminderInsert(date!!)

                    updatedate(date!!, userList[position].alarmId.toString())

                }.display()




          /*  val dialog = Dialog(viewesdata!!.context)
            Log.e("diloge","------------------------------------------>")
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.gettime)
            val header=dialog.findViewById<TextView>(R.id.headertextfortime)
            header.text = "Reminder Time"
            var data=""
            var day="am"
            var hours=0

            dialog.timecancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.timeok.setOnClickListener {
              //  userList[position].time=data





                // CustomAdapter.this

                dialog.dismiss()
            }
            dialog.gettime.setOnTimeChangedListener { timePicker, hour, minute ->

                if(hour>12){
                    day="pm"
                    hours =hour- 12
                } else{
                    hours=hour
                    day="am"
                }
                data="$hours:$minute $day"

                userList[position].time=data
                holder.itemView.time.text=data

               // updatedate("${hour}:$minute", userList[position].alarmId)
                Log.e("time", "Hour: "+ hours + " Minute : "+ minute+"day " +day)
            }
            dialog.show()*/
        }
//        holder.itemView.tablet_type.setOnClickListener {
//            val dialog = Dialog(viewesdata!!.context)
//            val datwfetch:String?
//            Log.e("diloge","------------------------------------------>")
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog.setCancelable(false)
//            dialog.setContentView(R.layout.dosage)
//            var data="1"
//            val name =dialog.findViewById<NumberPicker>(R.id.numberpicker)
//            val header=dialog.findViewById<TextView>(R.id.headertextfordata)
//            header.text = "Reminder"
//            val values = arrayOf("1 Tablet", "2 Tablet (s)", "3 Tablet (s)", "4 Tablet (s)", "5 Tablet (s)","6 Tablet (s)","7 Tablet (s)","8 Tablet (s)","9 Tablet (s)","10 Tablet (s)")
//            name.minValue = 0zzz
//            name.maxValue = values.size - 1
//            name.displayedValues = values
//            name.wrapSelectorWheel = true
//
//            dialog.calendercancel.setOnClickListener {
//                dialog.dismiss()
//            }
//            dialog.calenderok.setOnClickListener {
//                userList[position].tablet=data
//                holder.itemView.tablet_type.text=data
//                dialog.dismiss()
//            }
//            name.setOnValueChangedListener { _, _, i2 ->
//                Log.e("Picker","hours"+(i2+1))
//                data= values[i2]
//                    //(i2+1).toString()
//                userList[position].tablet=data
//                holder.itemView.tablet_type.text=data
//            }
//            dialog.show()
//        }
        holder.itemView.cut.setOnClickListener {

            Log.e("cut","${userList[position].alarmId!!}")
            AsyncTask.execute {
                var userId by stringPref("userId", null)
                var petid by stringPref("petid", null)
                try {

                    val callDetails = splootDB!!.petMasterDao()

                    val delete=callDetails.alarmdelete(userList[position].alarmId!!)

                    val viewall=callDetails.getdataalarm(userId!!, petid!!,"2")

                    userList=viewall

                    holder.itemView.post(Runnable {
                        notifyDataSetChanged()

                    })


                } catch (e: Exception) {
                    val s = e.message;
                    Log.e("Error",s)
                }
        }
    }



    }

    private fun updatedate(data: Date, id: String?) {
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var Id= id?.toLong()
            try {
                val callDetails = splootDB!!.petMasterDao()
                Log.e("check data","statedatre = $id,petid=$petid,userid:$data")

                val selected_data=callDetails.getforalarm(id!!)
                Log.e("updatedataBdroeen","__________________>>>>>>>>>>$selected_data")
                val insertdata= alarmdata(
                    alarmId = Id,
                    userId = userId,
                    petId = petid,
                    startdate = selected_data.startdate,
                    endate = selected_data.endate,
                    time =  data,
                    //notifieddata = selected_data.notifieddata,
                    active = "2"
                )
                Log.e("update--","eeedsksbkdkjkes $insertdata")
                val delete=callDetails.updatealarmdata(insertdata)
                val update_data=callDetails.getforalarm(id!!)
                Log.e("updatedata","__________________>>>>>>>>>>$update_data")

            } catch (e: Exception) {
                val s = e.message;
                Log.e("Error",s)
                // alarmid="0123"
            }
        }


    }



    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: alarmdata) {
            val textViewName = itemView.findViewById(R.id.time) as TextView
            val textViewAddress  = itemView.findViewById(R.id.tablet_type) as TextView


            var hours=user.time!!.hours

            var min=user.time!!.minutes

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

            textViewName.text ="$hr:$mi $day"
        }
    }

    fun dbprocess(){

       // getforalarm
    }


}