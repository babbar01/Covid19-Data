package com.example.covid19_data


import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class MyDateAxisValueFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        Log.d(HomeFragment.TAG, "getAxisLabel: $value")
        val timestamp = value.toLong()
        Log.d(HomeFragment.TAG, "getAxisLabel: $timestamp")

        val mdate = Date()
        mdate.time = timestamp

        val mdateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        return mdateFormatter.format(mdate)
    }
}