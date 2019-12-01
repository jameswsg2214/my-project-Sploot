package com.work.sploot.activities

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.sploot.Entity.doctor_list
import com.work.sploot.Entity.taskdata
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import com.work.sploot.data.taskviewdata
import kotlinx.android.synthetic.main.docter_listview.view.*
import kotlinx.android.synthetic.main.taskfragment.view.*

class Docter_Professional_list:Fragment() {

    private var splootDB: SplootAppDB? = null
    val arr = ArrayList<taskviewdata>()
    var localview:View?=null
    var recyclerView:RecyclerView?=null
    companion object {
        var viewdata: View? =null
        var cont: FragmentActivity?=null
        fun newInstance(view: View, mContext: FragmentActivity): Docter_Professional_list {
            viewdata=view
            cont=mContext
            return Docter_Professional_list()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views=inflater.inflate(R.layout.docter_listview, container, false)
        splootDB = SplootAppDB.getInstance(views.context)
        localview=views
        recyclerView=views.findViewById(R.id.doctor_recycler_view)

        views.add_doctor.setOnClickListener {
            val mContext = activity
            val fm = mContext?.supportFragmentManager
            val transaction = fm?.beginTransaction()
            transaction?.replace(R.id.doctor_fragment,Doctor_Professtional_add.newInstance(viewdata!!,cont!!))
            transaction?.commit()
        }
        dataprocess()
        return views
    }

    private fun dataprocess() {
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var user= userId?.toInt()
            var petId= petid?.toLong()
            try {
                val callDetails = splootDB!!.petMasterDao()
                val recyciew= viewdata!!.findViewById<RecyclerView>(R.id.doctor_recycler_view)
               // val ischeck=callDetails.ispresent_doctor()
                //if(ischeck) {
                val viewdata=callDetails.getAll_doctor_user(userId!!)
                Log.e("responce","no data found $viewdata")


                recyciew?.post(Runnable {
                    recyciew.layoutManager = LinearLayoutManager(localview?.context)
                    val adapter = doctorAdapter(viewdata)
                    recyciew.adapter = adapter

                })

            } catch (e: Exception) {
                val s = e.message
                Log.e("Errorqwed",s)
            }
        }

    }
}

class doctorAdapter(var userList: List<doctor_list>) : RecyclerView.Adapter<doctorAdapter.ViewHolder>() {
    var viewesdata:View?=null
    private var splootDB: SplootAppDB? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.doctor_recycler, parent, false)
        viewesdata=v
        Log.e("working","i am here")
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])



    }
    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: doctor_list) {
            val name = itemView.findViewById(R.id.doctor_name_view) as TextView
            val address  = itemView.findViewById(R.id.doctor_address) as TextView


            name.text = user.doctor_name
            address.text = user.doctor_Address
        }
    }


}
