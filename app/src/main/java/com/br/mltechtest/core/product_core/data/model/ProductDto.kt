package com.br.mltechtest.core.product_core.data.model

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pictures")
    val pictures: List<PictureDto>,
)