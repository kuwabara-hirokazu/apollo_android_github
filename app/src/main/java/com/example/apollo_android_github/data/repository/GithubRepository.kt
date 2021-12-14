package com.example.apollo_android_github.data.repository

import com.apollographql.apollo.api.Response
import com.example.apollo_android_github.AddReactionMutation
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.type.ReactionContent
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getGithubData(): Flow<Response<SearchQuery.Data>>

    fun addReaction(
        subjectId: String,
        content: ReactionContent
    ): Flow<Response<AddReactionMutation.Data>>
}
