package com.example.apollo_android_github.data.source.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toFlow
import com.example.apollo_android_github.AddReactionMutation
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.ViewerQuery
import com.example.apollo_android_github.type.ReactionContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GithubRemoteImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : GithubRemote {

    override fun getGithubData(): Flow<Response<ViewerQuery.Data>> {
        return apolloClient.query(ViewerQuery()).toFlow()
    }

    override fun addReaction(
        subjectId: String,
        content: ReactionContent
    ): Flow<Response<AddReactionMutation.Data>> {
        return apolloClient.mutate(AddReactionMutation(subjectId, content)).toFlow()
    }

    override fun searchRepository(userName: String): Flow<Response<SearchQuery.Data>> {
        return apolloClient.query(SearchQuery(userName, 20)).toFlow()
    }
}
