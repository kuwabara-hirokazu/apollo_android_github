package com.example.apollo_android_github.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toFlow
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.ViewerQuery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GithubRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : GithubRepository {
    override fun getGithubData(): Flow<Response<SearchQuery.Data>> {
        return apolloClient.query(ViewerQuery()).toFlow()
            .flatMapLatest {
                val name = it.data?.viewer?.login ?: ""
                searchRepository(name)
            }
    }

    private fun searchRepository(userName: String): Flow<Response<SearchQuery.Data>> {
        return apolloClient.query(SearchQuery(userName, 20)).toFlow()
    }
}
