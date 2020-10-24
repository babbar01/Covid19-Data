package com.example.covid19_data

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_graph.*


class GraphFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_graph, container, false)

        val scrollLayout = v.findViewById<ScrollView>(R.id.scroll_view_history_data)
        val scrollText = v.findViewById<TextView>(R.id.scroll_textView_history_data)

        val url = "https://api.apify.com/v2/datasets/58a4VXwBBF0HtxuQa/items?format=json&clean=1"

        var activeLineDataList : ArrayList<Entry> = ArrayList()

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                Log.d(HomeFragment.TAG, "onCreateView: response success on graph fragment")

                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val activeCases = jsonObject.getString("activeCases")
                    val lastUpdated = jsonObject.getString("lastUpdatedAtApify")

                    val lastUpdateDate = lastUpdated.split('T')[0]
                    Log.d(HomeFragment.TAG, "inforloop: $lastUpdateDate")
                    val listDate = lastUpdateDate.split('-')
                    Log.d(HomeFragment.TAG, "inforloop: $listDate")
                    val floatdate = listDate[0] + listDate[1] + listDate[2]
                    Log.d(HomeFragment.TAG, "onCreateView: $floatdate")

                    if(activeCases != "null") activeLineDataList.add(Entry(floatdate.toFloat(), activeCases.toFloat()))

                    scrollText.append("Active Cases : $activeCases \n Last Updated : $lastUpdated \n")
                    scrollLayout.fullScroll(ScrollView.FOCUS_DOWN)
                }

                val lineDataSet = LineDataSet(activeLineDataList, "Active Cases")

                lineDataSet.apply {
                    setDrawCircles(false)
                    lineWidth = 4F
                    if (container != null) {
                        valueTextColor = ContextCompat.getColor(container.context, R.color.white)
                        color = ContextCompat.getColor(container.context, R.color.color_active)
                        setCircleColor(ContextCompat.getColor(container.context, R.color.color_active))
                    }
                }

                val LineDataSets = arrayListOf<ILineDataSet>(lineDataSet)

                val LineData = LineData(LineDataSets)


                lineChart.apply {
                    data = LineData
                    if (container != null) {
                        xAxis.textColor = ContextCompat.getColor(container.context, R.color.white)
                        xAxis.setDrawGridLines(false)
                        xAxis.position = XAxis.XAxisPosition.BOTTOM

                        axisLeft.textColor = ContextCompat.getColor(container.context, R.color.white)
                        axisLeft.setDrawGridLines(false)

                        axisRight.isEnabled = false

                        description = null
                        legend.textColor = ContextCompat.getColor(container.context, R.color.white)
                        setBackgroundColor(ContextCompat.getColor(container.context,R.color.black))
                        animateXY(1500,1000)
                    }
                    invalidate()
                }

                progress_history_data.visibility = View.GONE
            },
            { error ->
                progress_history_data.visibility = View.GONE
                if (container != null) {
                    Toast.makeText(container.context, "${error.message}", Toast.LENGTH_LONG)
                }
                Log.d(HomeFragment.TAG, "onCreateView: ${error.message}")
                Log.d(HomeFragment.TAG, "onCreateView: ${error.printStackTrace()}")
                Log.d(HomeFragment.TAG, "onCreateView: error while loading history data")
            })

        if (container != null) {
            MySingleton.getInstance(container.context).addToRequestQueue(jsonArrayRequest)
            Log.d(HomeFragment.TAG, "onCreateView: request added for history data")
        }

        return v
    }


}