package com.br.mltechtest.core.product_core.data.service

import androidx.annotation.Keep
import com.br.mltechtest.core.product_core.data.model.ProductDetailsDto
import com.br.mltechtest.core.product_core.data.model.ProductSearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("products/search")
    suspend fun searchProducts(
        @Query("site_id") siteId: String = "MLB",
        @Query("q") query: String
    ): ProductSearchDto

    @Keep
    @GET("products/{productId}")
    suspend fun getProductDetails(
        @Path("productId") productId: String
    ): ProductDetailsDto
}