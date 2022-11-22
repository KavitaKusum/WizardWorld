package com.example.wizardworld.domain.model

data class House(var id:String="",
                 var name: String="",
                 var houseColours: String="",
                 var founder:String="",
                 var animal:String="",
                 var element: String="",
                 var ghost: String="",
                 var commonRoom:String="",
                 var heads:List<Heads>,
                 var traits:List<Traits>
)
data class Heads ( var id:String="",
                   var name: String="")
data class Traits ( var id:String="",
                    var name: String="")