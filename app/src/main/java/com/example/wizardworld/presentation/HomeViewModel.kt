package com.example.wizardworld.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.wizardworld.CHOICE
import com.example.wizardworld.R
import com.example.wizardworld.domain.model.Product

class HomeViewModel : ViewModel() {
    fun getProductList(context: Context): List<Product> {
        return listOf(
            Product(
                CHOICE.ONE.value,
                R.drawable.wizard,
                context.getString(R.string.bottom_nav_wizard_label)
            ),
            Product(
                CHOICE.TWO.value,
                R.drawable.house,
                context.getString(R.string.bottom_nav_house_label)
            ),
            Product(
                CHOICE.THREE.value,
                R.drawable.elixir,
                context.getString(R.string.bottom_nav_elixir_label)
            ),
            Product(
                CHOICE.FOUR.value,
                R.drawable.spell,
                context.getString(R.string.bottom_nav_spell_label)
            )
        )
    }
}