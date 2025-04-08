package com.br.mltechtest.core.product_core.domain.model

data class Product(
    val id: String,
    val name: String,
    val imagesUrl: List<String>,
)