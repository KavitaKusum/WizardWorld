package com.example.wizardworld.domain.model

data class Elixir(var name:String="",
                  var id:String="",
                  var effect:String="",
                  var sideEffects:String="",
                  var characteristics:String="",
                  var time:String="",
                  var difficulty:String="",
                  var ingredients:List<Ingredient>,
                  var inventors:List<Inventor>,
                  var manufacturer:String="",
)
data class Inventor ( var id:String="",
                   var name: String="")
data class Ingredient ( var id:String="",
                    var name: String="")