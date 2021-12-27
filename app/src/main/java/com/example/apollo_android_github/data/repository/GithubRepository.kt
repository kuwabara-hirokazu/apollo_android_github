package com.example.apollo_android_github.data.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.example.apollo_android_github.AddReactionMutation
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.type.ReactionContent
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getGithubData(): Flow<ApolloResponse<SearchQuery.Data>>

    fun addReaction(
        subjectId: String,
        content: ReactionContent
    ): Flow<ApolloResponse<AddReactionMutation.Data>>
}
