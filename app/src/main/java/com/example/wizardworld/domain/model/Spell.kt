package com.example.wizardworld.domain.model

data class Spell(
    val name: String,
    val incantation: String,
    val id: String,
    val effect: String,
    val canBeVerbal: String,
    val type: String,
    val light: String,
    val creator: String
) {
    constructor() : this("", "", "", "", "", "", "", "")
}