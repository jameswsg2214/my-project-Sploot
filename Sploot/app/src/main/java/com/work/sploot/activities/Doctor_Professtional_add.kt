package com.work.sploot.activities

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.work.sploot.Entity.doctor_list
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.ConstantMethods
import com.work.sploot.data.stringPref
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.professional_list.view.*

class Doctor_Professtional_add:Fragment() {

    private var splootDB: SplootAppDB? = null
    companion object {
        var viewdata: View? =null
        var cont: FragmentActivity?=null

        fun newInstance(view: View?, mContext: FragmentActivity?): Doctor_Professtional_add {
            viewdata=view
            cont=mContext
            return Doctor_Professtional_add()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cityarray = arrayOf("chennai","Coimbatire", "madurai","Erode")
        val statearray = arrayOf("Tamilnadu","kerala", "AP","Mumbai","Delhi")
        val countryarray = arrayOf("India","America", "china")
        val views=inflater.inflate(R.layout.professional_list, container, false)
        splootDB = SplootAppDB.getInstance(views.context)

        val country = ArrayAdapter<String>(
            views.context,
            android.R.layout.simple_dropdown_item_1line,
            countryarray
        )
        views.pro_country.setAdapter(country)
        views.pro_country.threshold = 1
        views.pro_country.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
          //  process(selectedItem)
         //   Toast.makeText(views.context,"Selected : $selectedItem", Toast.LENGTH_SHORT).show()
        }
        views.pro_country.setOnDismissListener {
            //  Toast.makeText(views.context,"Suggestion closed.", Toast.LENGTH_SHORT).show()
            // process(views.auto_complete_text_view.text.toString())
        }
        views.pro_country.onFocusChangeListener = View.OnFocusChangeListener{
                view, b ->
            if(b){
                views.pro_country.showDropDown()
            }
        }
        val city = ArrayAdapter<String>(
            views.context,
            android.R.layout.simple_dropdown_item_1line,
            cityarray
        )
        views.pro_profile_city.setAdapter(city)
        views.pro_profile_city.threshold = 1
        views.pro_profile_city.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
        }
        views.pro_profile_city.setOnDismissListener {
        }
        views.pro_profile_city.onFocusChangeListener = View.OnFocusChangeListener{
                view, b ->
            if(b){
                views.pro_profile_city.showDropDown()
            }
        }

        val state = ArrayAdapter<String>(
            views.context,
            android.R.layout.simple_dropdown_item_1line,
            statearray
        )
        views.pro_state.setAdapter(state)
        views.pro_state.threshold = 1
        views.pro_state.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
           // process(selectedItem)
            //Toast.makeText(views.context,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }
        views.pro_state.setOnDismissListener {
            //  Toast.makeText(views.context,"Suggestion closed.", Toast.LENGTH_SHORT).show()
            // process(views.auto_complete_text_view.text.toString())
        }
        views.pro_state.onFocusChangeListener = View.OnFocusChangeListener{
                view, b ->
            if(b){
                views.pro_state.showDropDown()
            }
        }
        views.doctor_close.setOnClickListener {
            val mContext = activity
            val fm = mContext?.supportFragmentManager
            val transaction = fm?.beginTransaction()
            transaction?.replace(R.id.doctor_fragment,Docter_Profressional_fragement.newInstance())
            transaction?.commit()
            Log.e("Clicked","sdfghjk")
        }
        views.profile_save.setOnClickListener {

            val validate= ConstantMethods().emailvalidation(views.pro_email_id.text.toString().trim())
            when{
                views.pro_name.text.toString().trim().isNullOrEmpty()->views.pro_name.error="Doctor name field can't be empty"
                views.pro_hospital_name.text.toString().trim().isNullOrEmpty()->views.pro_hospital_name.error="Hospital name field can't be empty"
                views.pro_phone_no.text.toString().trim().isNullOrEmpty()->views.pro_phone_no.error="Hospital number field can't be empty"
                views.pro_phone_no.text.toString().trim().length<10->views.pro_phone_no.error="Number Minimum 10 digits"
                views.pro_email_id.text.toString().trim().isNullOrEmpty()->views.pro_email_id.error="Email id field can't be empty"
                !validate->views.pro_email_id.error="Invalid Email Format"
                views.pro_address.text.toString().trim().isNullOrEmpty()->views.pro_address.error="Address field can't be empty"
                views.pro_country.text.toString().trim().isNullOrEmpty()->views.pro_country.error="Country field can't be empty"
                views.pro_state.text.toString().trim().isNullOrEmpty()->views.pro_state.error="State field can't be empty"
                views.pro_profile_city.text.toString().trim().isNullOrEmpty()->views.pro_profile_city.error="City field can't be empty"
                views.pro_pin.text.toString().trim().isNullOrEmpty()->views.pro_pin.error="PIN field can't be empty"

                else->{
                    process(views.pro_name.text.toString(),
                        views.pro_hospital_name.text.toString(),
                        views.pro_phone_no.text.toString(),
                        views.pro_email_id.text.toString().trim(),
                        views.pro_address.text.toString(),
                        views.pro_profile_city.text.toString(),
                        views.pro_pin.text.toString(),
                        views.pro_state.text.toString(),
                        views.pro_country.text.toString())
                    val mContext = activity
                    val fm = mContext?.supportFragmentManager
                    val transaction = fm?.beginTransaction()
                    transaction?.replace(R.id.doctor_fragment,Docter_Professional_list.newInstance(
                        viewdata!!, cont!!))
                    transaction?.commit()
                }
            }

           // Log.e("Clicked","sdfghjk")
        }
        return views
    }

    private fun process(
        toString: String,
        toString1: String,
        toString2: String,
        toString3: String,
        toString4: String,
        toString5: String,
        toString6: String,
        toString7: String,
        toString8: String
    ) {
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var username by stringPref("name", null)
            var petimage by stringPref("petimage", null)
            var user= userId?.toInt()
            var petId= petid?.toLong()
            try {
                //val callDetails = splootDB!!.petMasterDao().getAll()
                val viewdata = splootDB!!.petMasterDao().getSelectdata(petid!!,userId!!)
                var pet = doctor_list(
                    userId= user,
                    doctor_name = toString,
                    hospital_name = toString1,
                    doctor_no = toString2,
                    email_id = toString3,
                    doctor_Address = toString4,
                    city = toString5,
                    pin = toString6,
                    state = toString7,
                    country = toString8,
                    petTpye = "1"
                )
                val callDetails = splootDB!!.petMasterDao().doctor_insert(pet)
                val get = splootDB!!.petMasterDao().getAll_doctor()



                Log.e("tabledata",""+get)
            } catch (e: Exception) {
                val s = e.message
                Log.e("Error",s)
            }
        }
    }
}