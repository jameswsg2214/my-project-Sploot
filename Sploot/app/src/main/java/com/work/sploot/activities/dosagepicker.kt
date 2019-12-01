package com.work.sploot.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.work.sploot.R

class dosagepicker : Fragment() {
    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val views = inflater.inflate(R.layout.dosage, container, false)
        val name =views.findViewById<NumberPicker>(R.id.numberpicker)
        name.minValue=0
        name.maxValue=10
        name.setOnValueChangedListener { numberPicker, i, i2 ->
            Log.e("Picker","$i2")
        }
        return views
    }
}