package com.example.covid19_data

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    companion object{
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
                tvtotalCase.setText(response.getString("totalCases"))
                tvactiveCase.setText(response.getString("activeCases"))
                tvdeathCase.setText(response.getString("deaths"))
                tvdischargedCase.setText(response.getString("recovered"))

                progress_home.visibility = View.GONE
                homeContainer?.visibility = View.VISIBLE
                Log.d(TAG, "onCreateView: response success")
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