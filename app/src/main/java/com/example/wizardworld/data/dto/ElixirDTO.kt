package com.example.wizardworld.data.dto

import com.google.gson.annotations.SerializedName

data class ElixirDTO(@SerializedName("name") val name:String="",
                     @SerializedName("id") val id:String="",
                     @SerializedName("effect") val effect:String="",
                     @SerializedName("sideEffects") val sideEffects:String="",
                     @SerializedName("characteristics") val characteristics:String="",
                     @SerializedName("time") val time:String="",
                     @SerializedName("difficulty") val difficulty:String="",
                     @SerializedName("ingredients") val ingredients:List<IngredientDTO>?=null,
                     @SerializedName("inventors") val inventors:List<InventorDTO>?=null,
                     @SerializedName("manufacturer") val manufacturer:String="",
)
data class InventorDTO (@SerializedName("id") val id:String="",
                  @SerializedName("firstName") val firstName: String="",
                  @SerializedName("lastName") val lastName: String="")
data class IngredientDTO (@SerializedName("id") val id:String="",
                   @SerializedName("name") val name: String="")