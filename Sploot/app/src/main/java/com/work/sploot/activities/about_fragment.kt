package com.work.sploot.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.work.sploot.R
import kotlinx.android.synthetic.main.about.view.*

class about_fragment:Fragment() {

    companion object {

        fun newInstance(): about_fragment {
            return about_fragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views=inflater.inflate(R.layout.about, container, false)

        views.contect_close.setOnClickListener {
            val intent= Intent(views.context, firstpage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        return views
    }
}