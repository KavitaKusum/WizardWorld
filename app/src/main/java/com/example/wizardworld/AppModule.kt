package com.example.wizardworld

import com.example.wizardworld.data.api.AppConfig
import com.example.wizardworld.data.api.WizardService
import com.example.wizardworld.data.mapper.ElixirListAPiResponseMapper
import com.example.wizardworld.data.mapper.HouseListAPiResponseMapper
import com.example.wizardworld.data.mapper.SpellListAPiResponseMapper
import com.example.wizardworld.data.mapper.WizardListAPiResponseMapper
import com.example.wizardworld.domain.repository.ProductsRepoImpl
import com.example.wizardworld.domain.repository.ProductsRepoInterface
import com.example.wizardworld.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// @Module annotation which will make this class a module
// to inject dependency to other class within it's scope.
// @InstallIn(SingletonComponent::class) this will make
// this class to inject dependencies across the entire application.
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWizardUseCase(): WizardListUseCase = WizardListUseCase(provideProductsRepository())

    @Provides
    @Singleton
    fun provideSpellUseCase(): SpellListUseCase = SpellListUseCase(provideProductsRepository())

    @Provides
    @Singleton
    fun provideHouseUseCase(): HouseListUseCase = HouseListUseCase(provideProductsRepository())
    @Provides
    @Singleton
    fun provideElixirUseCase(): ElixirListUseCase = ElixirListUseCase(provideProductsRepository())

    @Provides
    @Singleton
    fun provideWizardDetailUseCase(): WizardDetailUseCase = WizardDetailUseCase(provideProductsRepository())

    @Provides
    @Singleton
    fun provideSpellDetailUseCase(): SpellDetailsUseCase = SpellDetailsUseCase(provideProductsRepository())

    @Provides
    @Singleton
    fun provideHouseDetailUseCase(): HouseDetailUseCase = HouseDetailUseCase(provideProductsRepository())
    @Provides
    @Singleton
    fun provideElixirDetailUseCase(): ElixirDetailUseCase = ElixirDetailUseCase(provideProductsRepository())

    @Provides
    @Singleton
    fun provideProductsRepository(): ProductsRepoInterface = ProductsRepoImpl(providesWizardService(),providesWizardMapper(),
        providesSpellMapper(),providesElixirMapper(),providesHouseMapper())

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
    @Singleton
    fun providesWizardService(): WizardService = AppConfig.WizardService()
}