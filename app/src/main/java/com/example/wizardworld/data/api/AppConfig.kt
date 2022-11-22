package com.example.wizardworld.data.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppConfig {

    // Base url of the api
    private const val BASE_URL = "https://wizard-world-api.herokuapp.com/"

    // create retrofit service
    fun WizardService(): WizardService =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WizardService::class.java)
}