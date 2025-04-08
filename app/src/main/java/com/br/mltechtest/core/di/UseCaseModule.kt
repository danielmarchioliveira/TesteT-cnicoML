package com.br.mltechtest.core.di

import com.br.mltechtest.core.product_core.domain.repository.ProductRepository
import com.br.mltechtest.core.product_core.domain.usecase.GetProductDetailsUseCase
import com.br.mltechtest.core.product_core.domain.usecase.SearchProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSearchProductsUseCase(
        repository: ProductRepository
    ): SearchProductsUseCase {
        return SearchProductsUseCase(
            repository = repository,
        )
    }

    @Provides
    @Singleton
    fun provideGetProductDetailsUseCase(
        repository: ProductRepository
    ): GetProductDetailsUseCase {
        return GetProductDetailsUseCase(
            repository = repository,
        )
    }
}