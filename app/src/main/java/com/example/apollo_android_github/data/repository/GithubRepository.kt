package com.example.apollo_android_github.data.repository

import com.apollographql.apollo.api.Response
import com.example.apollo_android_github.ViewerQuery
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getGithubData(): Flow<Response<ViewerQuery.Data>>
}
