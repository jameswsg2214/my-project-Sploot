package com.work.sploot.activities

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.work.sploot.Entity.activitynotes
import com.work.sploot.Entity.other
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import kotlinx.android.synthetic.main.others.view.*
import kotlinx.android.synthetic.main.weight.view.*
import java.text.SimpleDateFormat

class othersfragment: Fragment() {
    private var splootDB: SplootAppDB? = null
    companion object {

        fun newInstance(): othersfragment {
            return othersfragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val views = inflater.inflate(R.layout.others, container, false)

        splootDB = SplootAppDB.getInstance(views.context)

        views.otherssave.setOnClickListener {

            var data= views.othersdata.text.toString().trim()

            when{

                views.othersdata.text.toString().trim().isNullOrEmpty()->Toast.makeText(views.context,"Please add notes ",Toast.LENGTH_LONG).show()

                else->{

                    process(data)

                    Toast.makeText(views.context,"Notes Save Sucuessfully",Toast.LENGTH_LONG).show()

                }

            }
        }
        return views
    }

    private fun process(data: String) {
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var select_date by stringPref("select_date", null)

            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val output = formatter.parse(select_date)

            try {
                //val callDetails = splootDB!!.petMasterDao().getAll()

                var data_inset=other(
                    petId = petid,
                    userId = userId,
                    date = output,
                    other = data
                )

                val result = splootDB!!.petMasterDao().insert_other(data_inset)

                var view_all=splootDB!!.petMasterDao().getother()

                val mContext = activity
                val manager = mContext?.supportFragmentManager
                val transaction = manager?.beginTransaction()

                transaction?.replace(R.id.medicfragement, Medicine())
                transaction?.commit()

                Log.e("data",".......................$view_all")

            } catch (e: Exception) {
                val s = e.message
                Log.e("Error", s)
            }
        }
    }
}