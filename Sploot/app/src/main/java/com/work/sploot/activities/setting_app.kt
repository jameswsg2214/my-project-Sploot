package com.work.sploot.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.sploot.R
import kotlinx.android.synthetic.main.appsettings.view.*

class setting_app:Fragment() {

    companion object {

        fun newInstance(): setting_app {
            return setting_app()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views=inflater.inflate(R.layout.appsettings, container, false)

        views.app_settings_close.setOnClickListener {
            val intent= Intent(views.context, firstpage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        return views
    }
}