package com.br.mltechtest.core.product_core.data.model

import com.google.gson.annotations.SerializedName

data class ProductDetailsDto(
    @SerializedName("name") val name: String,
    @SerializedName("pictures") val pictures: List<PictureDto>?,
)