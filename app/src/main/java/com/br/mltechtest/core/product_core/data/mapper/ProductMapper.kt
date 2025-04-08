package com.br.mltechtest.core.product_core.data.mapper

import com.br.mltechtest.core.product_core.data.model.ProductDetailsDto
import com.br.mltechtest.core.product_core.data.model.ProductDto
import com.br.mltechtest.core.product_core.data.model.ProductSearchDto
import com.br.mltechtest.core.product_core.domain.model.Product
import com.br.mltechtest.core.product_core.domain.model.ProductDetails

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        imagesUrl = pictures.map { it.url },
    )
}

fun ProductSearchDto.toDomain(): List<Product> {
    return results.map { it.toDomain() }
}

fun ProductDetailsDto.toDomain(): ProductDetails {
    return ProductDetails(
        name = name,
        imagesUrl = pictures?.map { it.url }.orEmpty()
    )
}