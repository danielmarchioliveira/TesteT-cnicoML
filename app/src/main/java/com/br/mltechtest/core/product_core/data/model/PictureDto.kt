package com.br.mltechtest.core.product_core.data.model

import com.google.gson.annotations.SerializedName

data class PictureDto(
    @SerializedName("url")
    val url: String,
)