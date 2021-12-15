package com.example.apollo_android_github.data.source.remote

import com.apollographql.apollo.api.Response
import com.example.apollo_android_github.AddReactionMutation
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.ViewerQuery
import com.example.apollo_android_github.type.ReactionContent
import kotlinx.coroutines.flow.Flow

interface GithubRemote {
    fun getGithubData(): Flow<Response<ViewerQuery.Data>>

    fun addReaction(
        subjectId: String,
        content: ReactionContent
    ): Flow<Response<AddReactionMutation.Data>>

    fun searchRepository(userName: String): Flow<Response<SearchQuery.Data>>
}
