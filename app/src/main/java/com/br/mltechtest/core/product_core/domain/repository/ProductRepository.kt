package com.br.mltechtest.core.product_core.domain.repository

import com.br.mltechtest.core.product_core.domain.model.ProductDetails

interface ProductRepository {
    suspend fun searchProducts(query: String): Result<List<com.br.mltechtest.core.product_core.domain.model.Product>>
    suspend fun getProductDetails(productId: String): Result<ProductDetails>
}
