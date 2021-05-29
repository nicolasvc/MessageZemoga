package com.example.messagezemoga.entities


import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("bs")
    val bs: String, // harness real-time e-markets
    @SerializedName("catchPhrase")
    val catchPhrase: String, // Multi-layered client-server neural-net
    @SerializedName("name")
    val name: String // Romaguera-Crona
)