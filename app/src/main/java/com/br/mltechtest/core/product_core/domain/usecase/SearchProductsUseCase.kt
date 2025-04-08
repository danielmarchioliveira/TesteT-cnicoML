package com.br.mltechtest.core.product_core.domain.usecase

import com.br.mltechtest.core.product_core.domain.repository.ProductRepository

class SearchProductsUseCase(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(query: String): Result<List<com.br.mltechtest.core.product_core.domain.model.Product>> =
        repository.searchProducts(query)
}