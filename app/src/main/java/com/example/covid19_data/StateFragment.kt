package com.example.covid19_data

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class StateFragment : Fragment() {

    lateinit var recyclerView : RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter : StateDataListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_state, container, false)

        recyclerView = v.findViewById(R.id.recyclerViewStateData)
        if (container != null) {
            layoutManager = LinearLayoutManager(container.context,RecyclerView.VERTICAL,false)
        }
//        recyclerAdapter = MainActivity.stateData?.let { StateDataListAdapter(it) }!!
        recyclerAdapter = StateDataListAdapter()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter

        return v
    }
}