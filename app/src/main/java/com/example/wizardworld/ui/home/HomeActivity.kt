package com.example.wizardworld.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wizardworld.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
        setContentView(binding.root)
    }
}