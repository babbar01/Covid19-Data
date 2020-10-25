package com.example.covid19_data


import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class MyDateAxisValueFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        Log.d(HomeFragment.TAG, "getAxisLabel: $value")
        val date = String.format("%f", value)
        Log.d(HomeFragment.TAG, "getAxisLabel: $date")
        val formattedDate = date.substring(6,8) + "-" + date.substring(4,6) + "-"  + date.substring(0,4)
        return formattedDate
    }
}