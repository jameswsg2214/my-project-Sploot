package com.work.sploot.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import kotlinx.android.synthetic.main.medicine.view.*

class Medicine : Fragment() {
    private var splootDB: SplootAppDB? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views = inflater.inflate(R.layout.medicine, container, false)

        var vaccinationbtn=views.findViewById<Button>(R.id.vaccinationadd)
        var dewormingbtn=views.findViewById<Button>(R.id.dewormingadd)
        var othersbtn=views.findViewById<Button>(R.id.othersadd)
        var notebtn=views.findViewById<Button>(R.id.noteadd)

        val mContext = activity
        val manager = mContext?.supportFragmentManager
        val transaction = manager?.beginTransaction()

        vaccinationbtn.setOnClickListener {

            transaction?.replace(R.id.medicfragement, vaccination_update_fragment.newInstance(1))

            transaction?.addToBackStack("")

            transaction?.commit()
            views.viewformedic.visibility=View.VISIBLE
            views.mediclist.visibility=View.GONE

        }
        dewormingbtn.setOnClickListener {

            transaction?.replace(R.id.medicfragement, deworming_fragment())

            transaction?.addToBackStack("")
            transaction?.commit()


            views.viewformedic.visibility=View.VISIBLE
            views.mediclist.visibility=View.GONE

        }
        othersbtn.setOnClickListener {

            transaction?.replace(R.id.medicfragement, vaccination_update_fragment.newInstance(2))

           // transaction?.replace(R.id.medicfragement, vaccinationfrag.newInstance(3))

            transaction?.addToBackStack("")
            transaction?.commit()

            views.viewformedic.visibility=View.VISIBLE
            views.mediclist.visibility=View.GONE

        }

        notebtn.setOnClickListener {
            transaction?.replace(R.id.medicfragement, othersfragment.newInstance())
            transaction?.addToBackStack("")
            transaction?.commit()
            views.viewformedic.visibility=View.VISIBLE
            views.mediclist.visibility=View.GONE

        }




        //dewormingadd othersadd noteadd
        return views
    }




}