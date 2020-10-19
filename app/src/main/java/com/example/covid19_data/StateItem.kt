package com.example.covid19_data

import com.google.gson.annotations.SerializedName

data class StateItem (
	@SerializedName("region") val region : String,
	@SerializedName("totalInfected") val totalInfected : Int,
	@SerializedName("newInfected") val newInfected : Int,
	@SerializedName("recovered") val recovered : Int,
	@SerializedName("newRecovered") val newRecovered : Int,
	@SerializedName("deceased") val deceased : Int,
	@SerializedName("newDeceased") val newDeceased : Int
)