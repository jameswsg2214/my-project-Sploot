package com.work.sploot.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.sploot.R

class feedback_fragment:Fragment() {
    companion object {

        fun newInstance(): feedback_fragment {
            return feedback_fragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views=inflater.inflate(R.layout.feedback, container, false)

        return views
    }
}