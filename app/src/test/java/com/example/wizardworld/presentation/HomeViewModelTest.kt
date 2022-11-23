package com.example.wizardworld.presentation

import android.content.Context
import com.example.wizardworld.R
import com.example.wizardworld.data.elixir
import com.example.wizardworld.data.house
import com.example.wizardworld.data.spell
import com.example.wizardworld.data.wizard
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class HomeViewModelTest : TestCase() {
    private lateinit var viewModel: HomeViewModel
    public override fun setUp() {
        super.setUp()
        viewModel = HomeViewModel()
    }

    fun testGetProductList() {
        val context = mockk<Context>()
        coEvery { context.getString(R.string.bottom_nav_wizard_label) } returns wizard
        coEvery { context.getString(R.string.bottom_nav_spell_label) } returns spell
        coEvery { context.getString(R.string.bottom_nav_house_label) } returns house
        coEvery { context.getString(R.string.bottom_nav_elixir_label) } returns elixir
        assertEquals(viewModel.getProductList(context).size, 4)
    }
}