package com.example.wizardworld

import com.example.wizardworld.data.api.AppConfig
import com.example.wizardworld.data.api.WizardService
import com.example.wizardworld.data.mapper.ElixirListAPiResponseMapper
import com.example.wizardworld.data.mapper.HouseListAPiResponseMapper
import com.example.wizardworld.data.mapper.SpellListAPiResponseMapper
import com.example.wizardworld.data.mapper.WizardListAPiResponseMapper
import com.example.wizardworld.data.repository.ProductsRepoImpl
import com.example.wizardworld.data.repository.ProductsRepoInterface
import com.example.wizardworld.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// @Module annotation which will make this class a module
// to inject dependency to other class within it's scope.
// @InstallIn(SingletonComponent::class) this will make
// this class to inject dependencies across the entire application.
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideWizardUseCase(): WizardListUseCase = WizardListUseCase(provideProductsRepository())

    @Provides
    fun provideSpellUseCase(): SpellListUseCase = SpellListUseCase(provideProductsRepository())

    @Provides
    fun provideHouseUseCase(): HouseListUseCase = HouseListUseCase(provideProductsRepository())

    @Provides
    fun provideElixirUseCase(): ElixirListUseCase = ElixirListUseCase(provideProductsRepository())

    @Provides
    fun provideWizardDetailUseCase(): WizardDetailUseCase =
        WizardDetailUseCase(provideProductsRepository())

    @Provides
    fun provideSpellDetailUseCase(): SpellDetailsUseCase =
        SpellDetailsUseCase(provideProductsRepository())

    @Provides
    fun provideHouseDetailUseCase(): HouseDetailUseCase =
        HouseDetailUseCase(provideProductsRepository())

    @Provides
    fun provideElixirDetailUseCase(): ElixirDetailUseCase =
        ElixirDetailUseCase(provideProductsRepository())

    @Provides
    fun provideProductsRepository(): ProductsRepoInterface = ProductsRepoImpl(
        providesWizardService(), providesWizardMapper(),
        providesSpellMapper(), providesElixirMapper(), providesHouseMapper()
    )

    private fun providesWizardMapper(): WizardListAPiResponseMapper {
        return WizardListAPiResponseMapper()
    }

    private fun providesHouseMapper(): HouseListAPiResponseMapper {
        return HouseListAPiResponseMapper()
    }

    private fun providesElixirMapper(): ElixirListAPiResponseMapper {
        return ElixirListAPiResponseMapper()
    }

    private fun providesSpellMapper(): SpellListAPiResponseMapper {
        return SpellListAPiResponseMapper()
    }

    @Provides
    fun providesWizardService(): WizardService = AppConfig.WizardService()
}