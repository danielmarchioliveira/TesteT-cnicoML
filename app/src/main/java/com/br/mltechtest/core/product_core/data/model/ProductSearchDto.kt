package com.br.mltechtest.core.product_core.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductSearchDto(
    @SerializedName("results")
    val results: List<ProductDto>,
)