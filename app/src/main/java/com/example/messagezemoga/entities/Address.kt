package com.example.messagezemoga.entities


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    val city: String, // Gwenborough
    @SerializedName("geo")
    val geo: Geo,
    @SerializedName("street")
    val street: String, // Kulas Light
    @SerializedName("suite")
    val suite: String, // Apt. 556
    @SerializedName("zipcode")
    val zipcode: String // 92998-3874
)