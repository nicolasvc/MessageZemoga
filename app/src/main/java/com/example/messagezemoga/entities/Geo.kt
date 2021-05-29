package com.example.messagezemoga.entities


import com.google.gson.annotations.SerializedName

data class Geo(
    @SerializedName("lat")
    val lat: String, // -37.3159
    @SerializedName("lng")
    val lng: String // 81.1496
)