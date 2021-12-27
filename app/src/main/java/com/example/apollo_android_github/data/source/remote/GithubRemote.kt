package com.example.apollo_android_github.data.source.remote

import com.apollographql.apollo3.api.ApolloResponse
import com.example.apollo_android_github.AddReactionMutation
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.ViewerQuery
import com.example.apollo_android_github.type.ReactionContent
import kotlinx.coroutines.flow.Flow

interface GithubRemote {
    fun getGithubData(): Flow<ApolloResponse<ViewerQuery.Data>>

    fun addReaction(
        subjectId: String,
        content: ReactionContent
    ): Flow<ApolloResponse<AddReactionMutation.Data>>

    fun searchRepository(userName: String): Flow<ApolloResponse<SearchQuery.Data>>
}
