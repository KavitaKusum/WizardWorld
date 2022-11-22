package com.example.wizardworld.data.dto

import com.google.gson.annotations.SerializedName

data class SpellDTO (@SerializedName("name") val name: String="",
                     @SerializedName("incantation") val incantation: String="",
                     @SerializedName("id") val id:String="",
                     @SerializedName("effect") val effect:String="",
                     @SerializedName("canBeVerbal") val canBeVerbal: String="",
                     @SerializedName("type") val type: String="",
                     @SerializedName("light") val light:String="",
                     @SerializedName("creator") val creator:String=""
)