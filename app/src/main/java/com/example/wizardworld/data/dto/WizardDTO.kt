package com.example.wizardworld.data.dto

import com.google.gson.annotations.SerializedName

data class WizardDTO (@SerializedName("firstName") val firstName: String="",
                   @SerializedName("lastName") val lastName: String="",
                   @SerializedName("id") val id:String="",
                   @SerializedName("elixirs") val elixirs:List<ElixirDTO>?=null)


