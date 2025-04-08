package com.br.mltechtest.core.product_core.domain.exceptions

sealed class Exceptions(message: String? = null) : Exception(message) {
    data object Network : Exceptions("Erro de conexão")
    data object Server : Exceptions("Erro interno do servidor")
    data object Client : Exceptions("Requisição inválida")
    data object Unknown : Exceptions("Erro desconhecido")
}