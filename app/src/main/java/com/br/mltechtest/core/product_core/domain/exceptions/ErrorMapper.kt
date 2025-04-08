package com.br.mltechtest.core.product_core.domain.exceptions

import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

object ErrorMapper {
    fun map(throwable: Throwable): Exceptions {
        return when (throwable) {
            is UnknownHostException, is IOException -> Exceptions.Network
            is HttpException -> mapHttpCode(throwable.code())
            else -> Exceptions.Unknown
        }
    }

    private fun mapHttpCode(code: Int): Exceptions {
        return when (code) {
            in 400..499 -> Exceptions.Client
            in 500..599 -> Exceptions.Server
            else -> Exceptions.Unknown
        }
    }
}