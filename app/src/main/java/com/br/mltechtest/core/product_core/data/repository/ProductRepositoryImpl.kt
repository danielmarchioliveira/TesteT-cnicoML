package com.br.mltechtest.core.product_core.data.repository

import com.br.mltechtest.core.product_core.data.mapper.toDomain
import com.br.mltechtest.core.product_core.data.service.ProductService
import com.br.mltechtest.core.product_core.domain.exceptions.ErrorMapper
import com.br.mltechtest.core.product_core.domain.model.ProductDetails
import com.br.mltechtest.core.product_core.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ProductRepositoryImpl(
    private val service: ProductService,
    private val dispatcherIO: CoroutineContext = Dispatchers.IO
) : ProductRepository {

    override suspend fun searchProducts(query: String): Result<List<com.br.mltechtest.core.product_core.domain.model.Product>> {
        return withContext(dispatcherIO) {
            try {
                val response = service.searchProducts(query = query)
                Result.success(response.toDomain())
            } catch (e: Exception) {
                val error = ErrorMapper.map(e)
                //enviar erro para crashlytics
                Result.failure(error)
            }
        }
    }

    override suspend fun getProductDetails(productId: String): Result<ProductDetails> {
        return withContext(dispatcherIO) {
            try {
                val dto = service.getProductDetails(productId)
                Result.success(dto.toDomain())
            } catch (e: Exception) {
                val error = ErrorMapper.map(e)
                //enviar erro para crashlytics
                Result.failure(error)
            }
        }
    }
}