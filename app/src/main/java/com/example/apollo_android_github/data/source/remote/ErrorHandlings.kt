package com.example.apollo_android_github.data.source.remote

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.Operation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T : Operation.Data> Flow<ApolloResponse<T>>.handleError(): Flow<T> {
    return this.map {
        val data = it.data
        when (!it.hasErrors() && data != null) {
            true -> data
            false -> throw it.errors?.toApolloApiException() ?: ApolloApiException(listOf())
        }
    }
}

private fun List<Error>.toApolloApiException(): ApolloApiException {
    return this.mapNotNull { error ->
        error.nonStandardFields?.values?.firstOrNull() as? String
    }.let { messages ->
        ApolloApiException(messages)
    }
}

class ApolloApiException(val messages: List<String>) : Exception(messages.joinToString())
