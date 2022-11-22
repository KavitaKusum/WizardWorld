package com.example.wizardworld.presentation

import android.content.Context
import com.example.wizardworld.R
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class HomeViewModelTest : TestCase() {
    private lateinit var viewModel:HomeViewModel
    public override fun setUp() {
        super.setUp()
        viewModel=HomeViewModel()
    }
    fun testGetProductList() {
        val context = mockk<Context>()
        coEvery{context.getString(R.string.bottom_nav_wizard_label)} returns "Wizard"
        coEvery{context.getString(R.string.bottom_nav_spell_label)} returns "Wizard"
        coEvery{context.getString(R.string.bottom_nav_house_label)} returns "Wizard"
        coEvery{context.getString(R.string.bottom_nav_elixir_label)} returns "Wizard"
        assertEquals(viewModel.getProductList(context).size,4)
    }
}