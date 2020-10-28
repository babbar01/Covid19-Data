package com.example.covid19_data

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*


class MyMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    val tvContent : TextView = findViewById(R.id.markerTextView)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        tvContent.text = "${String.format("%.0f",e?.y)} at ${getTimeDate(e?.x)}"

        super.refreshContent(e, highlight)
    }

    private var mOffset: MPPointF? = null

    override fun getOffset(): MPPointF? {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }
        return mOffset
    }

    private fun getTimeDate(timeStamp: Float?): String? {

        if (timeStamp == null) return null
        val mdate = Date()
        mdate.time = timeStamp.toLong()

        val mdateFormatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)
        return mdateFormatter.format(mdate)
    }

}