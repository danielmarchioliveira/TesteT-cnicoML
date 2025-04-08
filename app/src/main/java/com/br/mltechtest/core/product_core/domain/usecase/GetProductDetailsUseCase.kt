package com.br.mltechtest.core.product_core.domain.usecase

import com.br.mltechtest.core.product_core.domain.model.ProductDetails
import com.br.mltechtest.core.product_core.domain.repository.ProductRepository

class GetProductDetailsUseCase(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(productId: String): Result<ProductDetails> =
        repository.getProductDetails(productId)
}