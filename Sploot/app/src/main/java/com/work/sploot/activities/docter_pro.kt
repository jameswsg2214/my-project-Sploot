package com.work.sploot.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.work.sploot.R
import kotlinx.android.synthetic.main.professional_list.view.*

class docter_pro:Fragment() {

    companion object {
        var type:String?=null
        fun newInstance(s: String): docter_pro {
            type=s
            return docter_pro()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views=inflater.inflate(R.layout.professional_list, container, false)
        Log.e("type","$type")


        val cityarray = arrayOf("select city","chennai","Coimbatire", "madurai","Erode")
        val statearray = arrayOf("select state","Tamilnadu","kerala", "AP","Mumbai","Delhi")
        val countryarray = arrayOf("select country","India","America", "china")
        views.pro_name.hint="Doctor Name"
        views.pro_hospital_name.hint="Hospital /clinic Name"
        var city=views.findViewById<Spinner>(R.id.pro_profile_city)
        var state=views.findViewById<Spinner>(R.id.pro_state)
        var country=views.findViewById<Spinner>(R.id.pro_country)

        var citydata=""
        var statedata=""
        var countryddata=""




        val spinnercity = ArrayAdapter(views.context, R.layout.spin_textview, cityarray)
        spinnercity.setDropDownViewResource(R.layout.spin_textview)
        city.adapter = spinnercity

        city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.e("masdfdd", "" + cityarray[position])
                citydata= cityarray[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                citydata= cityarray[1]
            }
        }


        val spinnerstate = ArrayAdapter(views.context, R.layout.spin_textview, statearray)
        spinnerstate.setDropDownViewResource(R.layout.spin_textview)
        state.adapter = spinnerstate

        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.e("masdfdd", "" + statearray[position])
                statedata= statearray[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                statedata= statearray[1]
            }
        }
        val spinnercountry = ArrayAdapter(views.context, R.layout.spin_textview, countryarray)
        spinnercountry.setDropDownViewResource(R.layout.spin_textview)
        country.adapter = spinnercountry

        country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.e("masdfdd", "" + countryarray[position])
                countryddata= countryarray[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                countryddata= countryarray[1]
            }
        }

        views.profile_save.setOnClickListener {
            Toast.makeText(views.context,"Saved successfully", Toast.LENGTH_LONG).show()
        }





        return views
    }
}