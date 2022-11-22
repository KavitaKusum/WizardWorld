package com.example.wizardworld.data.api
import com.example.wizardworld.data.dto.ElixirDTO
import com.example.wizardworld.data.dto.HouseDTO
import com.example.wizardworld.data.dto.SpellDTO
import com.example.wizardworld.data.dto.WizardDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WizardService {
    @GET("/Wizards")
    suspend fun getWizards(): Response<List<WizardDTO>>
    @GET("/Wizards/{id}")
    suspend fun getWizardById(@Path("id") id: String): Response<WizardDTO>

    @GET("/Houses")
    suspend fun getHouses(): Response<List<HouseDTO>>
    @GET("/Houses/{id}")
    suspend fun getHouseById(@Path("id") id: String): Response<HouseDTO>


    @GET("/Spells")
    suspend fun getSpells(): Response<List<SpellDTO>>
    @GET("/Spells/{id}")
    suspend fun getSpellById(@Path("id") id: String): Response<SpellDTO>

    @GET("/Elixirs")
    suspend fun getElixirs(): Response<List<ElixirDTO>>
    @GET("/Elixirs/{id}")
    suspend fun getElixirById(@Path("id") id: String): Response<ElixirDTO>
}