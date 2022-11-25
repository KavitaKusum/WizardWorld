package com.example.wizardworld.domain.model

data class Elixir(
    val name: String,
    val id: String,
    val effect: String,
    val sideEffects: String,
    val characteristics: String,
    val time: String,
    val difficulty: String,
    val manufacturer: String,
    val inventors: List<String>,
    val ingredients: List<String>
) {
    constructor() : this("","","","","","","","", emptyList(), emptyList())
}
