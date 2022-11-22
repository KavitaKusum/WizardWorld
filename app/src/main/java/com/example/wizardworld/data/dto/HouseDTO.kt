package com.example.wizardworld.data.dto

import com.google.gson.annotations.SerializedName

data class HouseDTO(@SerializedName("id") val id:String="",
                    @SerializedName("name") val name: String="",
                    @SerializedName("houseColours") val houseColours: String="",
                    @SerializedName("founder") val founder:String="",
                    @SerializedName("animal") val animal:String="",
                    @SerializedName("element") val element: String="",
                    @SerializedName("ghost") val ghost: String="",
                    @SerializedName("commonRoom") val commonRoom:String="",
                    @SerializedName("heads") val heads:List<HeadsDTO>?=null,
                    @SerializedName("traits") val traits:List<TraitsDTO>?=null
)
data class HeadsDTO (@SerializedName("id") val id:String="",
                  @SerializedName("firstName") val firstName: String="",
                  @SerializedName("lastName") val lastName: String="")
data class TraitsDTO (@SerializedName("id") val id:String="",
                   @SerializedName("name") val name: String="")