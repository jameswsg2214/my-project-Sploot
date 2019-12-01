package com.work.sploot.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.sploot.R

class Docter_Profressional_fragement:Fragment(){

    companion object {
        fun newInstance(): Docter_Profressional_fragement {
            return Docter_Profressional_fragement()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views=inflater.inflate(R.layout.doctor_fragment, container, false)
        val mContext = activity
        val fm = mContext?.supportFragmentManager
        val transaction = fm?.beginTransaction()
        transaction?.replace(R.id.doctor_fragment, Docter_Professional_list.newInstance(views,mContext))
        transaction?.commit()
        return views
    }

}