package com.example.covid19_data

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateDataListAdapter :
    RecyclerView.Adapter<StateDataListAdapter.MyviewHolder>() {

    var stateListData: ArrayList<StateItem>? = MainActivity.stateData

    class MyviewHolder : RecyclerView.ViewHolder {

        var stateName: TextView
        var stateTxtActive: TextView
        var stateTxtRecovered: TextView
        var stateTxtDeaths: TextView

        constructor(itemView: View) : super(itemView) {

            stateName = itemView.findViewById(R.id.stateName)
            stateTxtActive = itemView.findViewById(R.id.state_txt_active)
            stateTxtRecovered = itemView.findViewById(R.id.state_txt_recovered)
            stateTxtDeaths = itemView.findViewById(R.id.state_txt_deaths)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.state_data_list_item, parent, false)
        return MyviewHolder(v)
    }

    override fun getItemCount(): Int {
        Log.d(HomeFragment.TAG, "getItemCount: ${stateListData?.size}")
        return stateListData!!.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        Log.d(HomeFragment.TAG, "onBindViewHolder: ${stateListData?.get(position)?.region}")
        holder.stateName.text = stateListData?.get(position)?.region
        holder.stateTxtActive.text = stateListData?.get(position)?.totalInfected.toString()
        holder.stateTxtRecovered.text = stateListData?.get(position)?.recovered.toString()
        holder.stateTxtDeaths.text = stateListData?.get(position)?.deceased.toString()
    }
}