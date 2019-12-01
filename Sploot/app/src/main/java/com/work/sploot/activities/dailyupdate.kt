package com.work.sploot.activities

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import kotlinx.android.synthetic.main.dayhealthactivity.view.*
import java.text.SimpleDateFormat
import java.util.*

class dailyupdate: Fragment() {

    var viewers:View?=null
    companion object {

        fun newInstance(): dailyupdate {


            var select_date by stringPref("select_date", null)

            if(select_date==null){

                val cur=Date()

                Log.e("dayef","data")

                val formatter = SimpleDateFormat("dd/MM/yyyy")

                val date=formatter.format(cur)

                select_date=date

            }
            else{

                Log.e("dayef","not found  $select_date")

            }


            return dailyupdate()


        }
    }

    private var splootDB: SplootAppDB? = null

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.dayhealthactivity, container, false)

        viewers=root

        val mContext = activity

        val fm = mContext?.supportFragmentManager

        val transaction = fm?.beginTransaction()


        val data=Date()

        val formatter = SimpleDateFormat("dd/MM/yyyy")

        val selet=formatter.format(data)

        val select=formatter.parse(selet)

        transaction?.replace(R.id.frag_for_view, view_pager_fragement.newInstance(selet, 0))

        transaction?.commit()

        val mDays = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        var select_date by stringPref("select_date", null)

        val mCalendar = Calendar.getInstance();

        var mToday = mCalendar.get(Calendar.DAY_OF_MONTH);

        var mToday1 = mCalendar.get(Calendar.MONTH); // zero based

        var mToday2 = mCalendar.get(Calendar.YEAR);

        root.calender_month_day.text = mDays[mToday1] + "-" + mToday2

        //root.week_Calendar.reset()

        val cal = Calendar.getInstance()

        root.previous_month_day.setOnClickListener {

            if (cal.get(Calendar.MONTH) == 0) {
                // cal.add(Calendar.YEAR,-1)
            }
            cal.add(Calendar.MONTH, -1)
            mToday = 0;
            mToday1 = cal.get(Calendar.MONTH); // zero based
            mToday2 = cal.get(Calendar.YEAR);

            root.calender_month_day.text = mDays[mToday1] + "-" + mToday2
            Log.e("month.....dadad", "next $mToday1")

        }


        root.calender_month_day.setOnClickListener {

          //  root!!.week_Calendar.reset()

            root.week_Calendar.moveToNext()
        }

        root.next_month_day.setOnClickListener {
            if (cal.get(Calendar.MONTH) == 11) {
                // cal.add(Calendar.YEAR,1)
            }
            cal.add(Calendar.MONTH, 1)
            mToday = 0;
            mToday1 = cal.get(Calendar.MONTH); // zero based
            mToday2 = cal.get(Calendar.YEAR);


            root.calender_month_day.text = mDays[mToday1] + "-" + mToday2
            //  cal . add (Calendar.YEAR, 1)

        }

        splootDB = SplootAppDB.getInstance(root.context)



        if(selet==select_date){

            Log.e("slaected12","same$select_date")
        }

        else{

            Log.e("slaected12","dif$select_date")

         /*   val output=formatter.format(select)

            val form=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")




            val output1=form.format(select)

            val formatter2 = DateTime.parse(output1)

            root.week_Calendar.setSelectedDate(formatter2)
*/

        }




        Log.e("slaected12","jhaja$select_date")

        /*root.week_Calendar.setOnTouchListener { v, event ->
            when (event?.action) {

                MotionEvent.ACTION_DOWN -> {
                    Log.e("selected ", "movedown")
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.e("selected ", "move")
                }
            }

            v?.onTouchEvent(event) ?: true
        }*/



        root.week_Calendar.setOnDateClickListener { dayOfMonth ->

            val inputMethodManager =
                root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

            val dayaaa = dayOfMonth

            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

            //for

            val monthformat = SimpleDateFormat("MM")

            val yearformat = SimpleDateFormat("yyyy")

            val dateformat = SimpleDateFormat("dd")

            val formattedmonth = monthformat.format(parser.parse("$dayOfMonth"))

            val formatyear = yearformat.format(parser.parse("$dayOfMonth"))

            val formatdate = dateformat.format(parser.parse("$dayOfMonth"))

//            Log.e("month","$formattedmonth-$formatyear")

            root.calender_month_day.text = mDays[(formattedmonth.toInt()) - 1] + "-" + formatyear

            cal.set(formatyear.toInt(), formattedmonth.toInt() - 1, formatdate.toInt())

            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val formattedDate = formatter.format(parser.parse("$dayOfMonth"))

            select_date = formattedDate

            val mContext = activity

            val fm = mContext?.supportFragmentManager

            val transaction = fm?.beginTransaction()

            transaction?.replace(R.id.frag_for_view, view_pager_fragement.newInstance(formattedDate,0))

            transaction?.commit()


        }
        return root
    }



    override fun onDestroy() {

       // viewers!!.weekCalendar.reset()
        super.onDestroy()

    }


}
