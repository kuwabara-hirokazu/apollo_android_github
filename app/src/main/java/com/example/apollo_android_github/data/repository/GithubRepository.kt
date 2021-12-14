package com.example.apollo_android_github.data.repository

import com.apollographql.apollo.api.Response
import com.example.apollo_android_github.SearchQuery
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getGithubData(): Flow<Response<SearchQuery.Data>>
}
