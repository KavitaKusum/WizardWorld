package com.example.wizardworld.domain.model

data class House(
    val id: String,
    val name: String,
    val houseColours: String,
    val founder: String,
    val animal: String,
    val element: String,
    val ghost: String,
    val commonRoom: String,
    val heads: List<String>,
    val traits: List<String>
) {
    constructor() : this("","","","","","","","", emptyList(), emptyList())
}