package com.work.sploot.activities

import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.sploot.Entity.madicineType
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import kotlinx.android.synthetic.main.taskfragment.view.*
import kotlinx.android.synthetic.main.taskrecycler.view.*
import java.text.SimpleDateFormat


class taskfragmentActivity:Fragment() {
    private var splootDB: SplootAppDB? = null
//    val arr = ArrayList<taskviewdata>()

    var ischeck=true
    var localview:View?=null

    companion object {
        var viewdata: View? =null
        var cont:FragmentActivity?=null
        fun newInstance(
    view: View, mContext: FragmentActivity
): taskfragmentActivity {
            viewdata=view
            cont=mContext
            return taskfragmentActivity()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views=inflater.inflate(R.layout.taskfragment, container, false)

        splootDB = SplootAppDB.getInstance(views.context)

        localview=views

        AsyncTask.execute {

            var userId by stringPref("userId", null)

            var petid by stringPref("petid", null)

            var user = userId?.toInt()

            var petId = petid?.toLong()

            try {

                val callDetails = splootDB!!.petMasterDao()

                val findtable = callDetails.find_task(petid!!, userId!!)

                if (findtable) {

                   views.task_menu.post(Runnable {
                       views.task_menu.visibility=View.VISIBLE
                   })
                }
                else {

                    views.task_menu.post(Runnable {
                        views.task_menu.visibility=View.GONE
                    })

                }
            }
            catch (e:Exception){

            }
        }


        views.add_task.setOnClickListener {

            val fm = cont?.supportFragmentManager

            val transaction = fm?.beginTransaction()

           // transaction?.replace(R.id.task_frame,addtaskactivity.newInstance(viewdata, cont))

            transaction?.replace(R.id.task_frame,add_task_fregment.newInstance(viewdata, cont,3))

            transaction?.commit()

            Log.e("OnClick","onclick")

        }

        views.task_done.setOnClickListener {


            val toast_show=Toast.makeText(this.context,"Please select task",Toast.LENGTH_SHORT)

            AsyncTask.execute {

                var userId by stringPref("userId", null)

                var petid by stringPref("petid", null)

                var user = userId?.toInt()

                var petId = petid?.toLong()

                try {

                    val callDetails = splootDB!!.petMasterDao()

                    val inactive = callDetails.find_in_active()

                    if (!inactive) {

                        toast_show.show()

                    }
                    else{


                        activity!!.runOnUiThread {
                           // showDialog()

                            deletetask(0)

                        }

                    }

                }
                catch (e: Exception) {
                    val s = e.message
                    Log.e("Error",s)
                    //alertDialog.dismiss()
                }
            }

        }

        views.task_delete.setOnClickListener {

            val toast_show=Toast.makeText(this.context,"Please select task",Toast.LENGTH_SHORT)

            AsyncTask.execute {

                var userId by stringPref("userId", null)

                var petid by stringPref("petid", null)

                var user = userId?.toInt()

                var petId = petid?.toLong()

                try {

                    val callDetails = splootDB!!.petMasterDao()

                    val inactive = callDetails.find_in_active()

                    if (!inactive) {

                        toast_show.show()

                    }
                    else{


                        activity!!.runOnUiThread {
                            showDialog()

                        }

                    }

                }
                catch (e: Exception) {
                    val s = e.message
                    Log.e("Error",s)
                    //alertDialog.dismiss()
                }
            }

          /*  Log.e("no data","agaayujh")
         //   showDialog()

            AsyncTask.execute {

                var userId by stringPref("userId", null)

                var petid by stringPref("petid", null)

                var user = userId?.toInt()

                var petId = petid?.toLong()

                try {

                    val callDetails = splootDB!!.petMasterDao()

                    val findtable = callDetails.findtask(petid!!, userId!!)

                    if (findtable) {

                            showDialog()

                    }
                    else {

                        Log.e("no data","agaayujh")

                    }
                }
                catch (e:Exception){

                }
            }*/

        }

        dataprocess()

        return views
    }


    private fun showDialog(){
        // Late initialize an alert dialog object
         var dialog:AlertDialog


        // Initialize a new instance of alert dialog builder object

        val builder = AlertDialog.Builder(localview!!.context)

        builder.setTitle("Delete Task")

        // Set a message for alert dialog
        builder.setMessage("Are you sure you want to delete?")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE ->
                {
                    deletetask(0)
                }
                DialogInterface.BUTTON_NEGATIVE ->
                {
                    //toast("Negative/No button clicked.")


                }
               // DialogInterface.BUTTON_NEUTRAL -> toast("Neutral/Cancel button clicked.")
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO",dialogClickListener)

        // Set the alert dialog neutral/cancel button
       // builder.setNeutralButton("CANCEL",dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()

    }

    private fun deletetask(type:Int) {
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var user= userId?.toInt()
            var petId= petid?.toLong()
            try {

                val callDetails = splootDB!!.petMasterDao()

                if(type!=0){

                }
                else{

                    val inactive=callDetails.find_in_active()

                    if(inactive){

                        callDetails.delete_task()

                        val all=callDetails.getAll_cat_()

                        dataprocess()

                        Log.e("delete task","data $all")

                    }
                    else{

                        Log.e("delete task","no data")

                    }

                }

            } catch (e: Exception) {
                val s = e.message
                Log.e("Error",s)
            }
        }


    }

    private fun dataprocess() {
        AsyncTask.execute {

            var userId by stringPref("userId", null)

            var petid by stringPref("petid", null)

            var user= userId?.toInt()

            var petId= petid?.toLong()

            try {

                val callDetails = splootDB!!.petMasterDao()

                val findtable = callDetails.find_task(petid!!,userId!!)

                val recyclerView= localview!!.findViewById<RecyclerView>(R.id.task_list)

                if(findtable){

                    Log.e("petdataget"," data correct")

                    val viewdatas=callDetails.getAll_Task(userId!!,petid!!)

                    Log.e("responce","no data found $viewdata")

                    viewdata!!.no_task.post(Runnable {

                        viewdata!!.no_task.visibility=View.GONE

                    })


                    recyclerView.post(Runnable {

                        recyclerView.visibility=View.VISIBLE

                        recyclerView.layoutManager = LinearLayoutManager(localview?.context)

                        val adapter = taskAdapter(viewdatas)

                        recyclerView.adapter = adapter

                     // alertDialog.dismiss()

                    })

                }
                else{

                    viewdata!!.no_task.post(Runnable {

                        viewdata!!.no_task.visibility=View.VISIBLE

                    })

                    viewdata!!.task_menu.post(
                        Runnable {

                            viewdata!!.task_menu.visibility=View.GONE

                        }

                    )



                    recyclerView.post(Runnable {

                        recyclerView.visibility=View.GONE

                    })

/*
                    val sdf = SimpleDateFormat("dd/MM/yyyy")

                    val dateInString = sdf.format(Date())

                    val formatter = SimpleDateFormat("dd/MM/yyyy")

                    val date = formatter.parse(dateInString)



                    val insetdata= taskdata(
                        userId = userId,
                        petId = petid,
                        task_name = "Morning Walking",
                        startdate = date,
                        enddate = date,
                        active = "1"
                    )
                    val insert=callDetails.taskinsertAll(insetdata)
                    val viewdata=callDetails.getAlltask()
                    Log.e("responce","no data found $viewdata")

                    recyclerView.post(Runnable {
                        recyclerView.layoutManager = LinearLayoutManager(localview?.context)
                        val adapter = taskAdapter(viewdata)
                        recyclerView.adapter = adapter
                   //     alertDialog.dismiss()
                    })*/

                }
            } catch (e: Exception) {
                val s = e.message
                Log.e("Error",s)
                //alertDialog.dismiss()
            }
            //alertDialog.dismiss()
        }

    }
}

class taskAdapter(var userList: List<madicineType>) : RecyclerView.Adapter<taskAdapter.ViewHolder>() {

    var viewesdata:View?=null

    var ischick=true

    private var splootDB: SplootAppDB? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.taskrecycler, parent, false)
        viewesdata=v
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(userList[position])

        splootDB = SplootAppDB.getInstance(viewesdata!!.context)

        //notifyDataSetChanged()

   /*     holder.itemView.radio_Task_Btn.setOnClickListener {

            if (ischick) {

                holder.itemView.radio_Task_Btn.isChecked = true

             //   ischick=false

                process(userList[position].allTypeId,0)

            }
            else{

                holder.itemView.radio_Task_Btn.isChecked = false

                ischick=true

                process(userList[position].allTypeId,1)

               // notifyDataSetChanged()
            }

            notifyDataSetChanged()


        }*/


//        holder.itemView.radio_Task_Btn.setChecked((userList[position].active).to)


        holder.itemView.radio_Task_Btn.setOnCheckedChangeListener { compoundButton, b ->

            if(b){

                process(userList[position].allTypeId,0)

            }
            else{
                process(userList[position].allTypeId,1)


            }

/*

            AsyncTask.execute {

                var userId by stringPref("userId", null)

                var petid by stringPref("petid", null)

                var user= userId?.toInt()

                var petId= petid?.toLong()

                try {

                    val callDetails = splootDB!!.petMasterDao()

                    val findtable = callDetails.find_task(petid!!, userId!!)

                  //  val recyclerView = localview!!.findViewById<RecyclerView>(R.id.task_list)

                    if (findtable) {

                        Log.e("petdataget", " data correct")

                        val viewdatas = callDetails.getAll_Task(userId!!, petid!!)

                        userList=viewdatas


                        notifyDataSetChanged()

                    }
                }catch (e:Exception)
                {

                    Log.e("eroo","$e")
                }
            }
*/




                        //notifyDataSetChanged()
        }





    }

    private fun process(taskId: Long?, i: Int) {
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var user= userId?.toInt()
            var petId= petid?.toLong()

            try {

                val callDetails = splootDB!!.petMasterDao()

                val getdata=callDetails.get_Select_Task(taskId!!)

               // var data:madicineType

                Log.e("click","$i")

                if(i==0){

                  val data=madicineType(
                      allTypeId = getdata.allTypeId,
                        userId = userId,
                        petId = petid,
                        task_name = getdata.task_name,
                        start_date = getdata.start_date,
                        end_date = getdata.end_date,
                        repeat_type = getdata.repeat_type,
                        frequency_type_id = getdata.frequency_type_id,
                        every_frequency = getdata.every_frequency,
                        selective_week = getdata.selective_week,
                        reminder_time = getdata.reminder_time,
                        cat_type = getdata.cat_type,
                        active = i
                    )

                    callDetails.update_task(data)

                    val get_data=callDetails.get_Select_Task(taskId!!)

                    val all=callDetails.getAll_cat_()

                    Log.e("active1","$get_data\n $data")

                    Log.e("active1 all","$all")


                }
                else{

                    val data=madicineType(
                        allTypeId = getdata.allTypeId,
                        userId = userId,
                        petId = petid,
                        task_name = getdata.task_name,
                        start_date = getdata.start_date,
                        end_date = getdata.end_date,
                        repeat_type = getdata.repeat_type,
                        frequency_type_id = getdata.frequency_type_id,
                        every_frequency = getdata.every_frequency,
                        selective_week = getdata.selective_week,
                        reminder_time = getdata.reminder_time,
                        cat_type = getdata.cat_type,
                        active = i
                    )

                    callDetails.update_task(data)

                    val get_data=callDetails.get_Select_Task(taskId!!)

                    val all=callDetails.getAll_cat_()

                    Log.e("active1","$get_data")

                    Log.e("active1 all","$all")

                }



            } catch (e: Exception) {
                val s = e.message
                Log.e("Error",s)
            }
        }
    }

    override fun getItemCount(): Int {

        return userList.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: madicineType) {
            val name = itemView.findViewById(R.id.task_name) as TextView
            val date  = itemView.findViewById(R.id.task_date) as TextView
            val check  = itemView.findViewById(R.id.radio_Task_Btn) as CheckBox
            name.text = user.task_name

            var select_Date= user.start_date

            val sdf = SimpleDateFormat("dd/MM/yyyy")


            val dateInString = sdf.format(select_Date)

            date.text = dateInString



         /*   check.setOnCheckedChangeListener { compoundButton, b ->

                if (b) {

                    //process(user].allTypeId,0)

                    Log.e("datas","${user.allTypeId}")

                } else {
                    //process(userList[position].allTypeId,1)

                    Log.e("datas","${user.allTypeId}")


                }
            }
*/
            /*
            if(user.active==0)
            {
                check.isChecked=true
            }else
            {
                check.isChecked=false
            }*/
        }
    }


}
