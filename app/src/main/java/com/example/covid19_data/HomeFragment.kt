package com.example.covid19_data

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    companion object {
        val TAG = "mytag"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        val url =
            "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true"

        val homeContainer = v.findViewById<ConstraintLayout>(R.id.homeFragment)

        homeContainer?.visibility = View.GONE

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val confirmedCases = response.getString("totalCases").toInt()
                val activeCases = response.getString("activeCases")
                val deathCases = response.getString("deaths").toInt()
                val recoveredCases = response.getString("recovered").toInt()
                val newActive = response.getString("activeCasesNew").toInt()
                val newRecovered = response.getString("recoveredNew").toInt()
                val newDeaths = response.getString("deathsNew").toInt()
                val previousDayTest = response.getString("previousDayTests").toInt()

                txt_total.text = confirmedCases.toString()
                txt_deaths.text = deathCases.toString()
                txt_recovered.text = recoveredCases.toString()
                txt_newActive.text = newActive.toString()
                txt_newRecovered.text = newRecovered.toString()
                txt_newDeaths.text = newDeaths.toString()
                txt_previousDayTests.text = previousDayTest.toString()

                // setting up pie chart

                val entries: MutableList<PieEntry> = ArrayList()

                entries.add(PieEntry(confirmedCases.toFloat(), "Confirmed"))
                entries.add(PieEntry(deathCases.toFloat(), "Deaths"))
                entries.add(PieEntry(recoveredCases.toFloat(), "Recovered"))

//                val colorsArray = arrayOf<Int>(
//                    R.color.color_confirmed,
//                    R.color.color_death,
//                    R.color.color_recovered
//                )

                val set = PieDataSet(entries, "Corona Cases")
                set.setDrawValues(false)
                if (container != null) {
                    set.setColors(
                        ContextCompat.getColor(container.context, R.color.color_confirmed),
                        ContextCompat.getColor(container.context,  R.color.color_death),
                        ContextCompat.getColor(container.context, R.color.color_recovered)
                    )
                }
                val data = PieData(set)

                pieChart.legend
                pieChart.apply {
                    this.data = data
                    setDrawEntryLabels(false)
                    holeRadius = 70f
                    centerText = "$activeCases Cases Active"
                    if (container != null) {
                        setCenterTextColor(ContextCompat.getColor(container.context,R.color.pieCenterText))
                        setHoleColor(ContextCompat.getColor(container.context,R.color.card_grey))
                    }

                    legend.isEnabled = false
                    setDrawEntryLabels(false)
                    description.isEnabled = false

                    animateXY(1000,1000)
                }


                progress_home.visibility = View.GONE
                homeContainer?.visibility = View.VISIBLE
                Log.d(TAG, "onCreateView: response success")


                pieChart.invalidate()



            },
            Response.ErrorListener { error ->
//                Toast.makeText(this,error.message,Toast.LENGTH_SHORT)
                progress_home.visibility = View.GONE
                Log.d(TAG, "onCreateView: error: ${error.message}")
            }
        )


        if (container != null) {
            Log.d(TAG, "onCreateView: container not null")
            MySingleton.getInstance(container.context).addToRequestQueue(jsonObjectRequest)
        }

        return v
    }


}