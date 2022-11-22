package com.example.wizardworld.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.wizardworld.R
import com.example.wizardworld.domain.model.Product

class HomeViewModel  : ViewModel() {
	fun getProductList(context: Context): List<Product> {
		return listOf(
            Product(1, R.drawable.wizard, context.getString(R.string.bottom_nav_wizard_label)),
			Product(2,R.drawable.house,context.getString(R.string.bottom_nav_house_label)),
			Product(3,R.drawable.elixir,context.getString(R.string.bottom_nav_elixir_label)),
			Product(4,R.drawable.spell,context.getString(R.string.bottom_nav_spell_label))
        )
	}
}