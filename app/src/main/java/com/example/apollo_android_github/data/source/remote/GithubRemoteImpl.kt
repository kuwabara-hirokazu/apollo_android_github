package com.example.apollo_android_github.data.source.remote

import com.apollographql.apollo3.ApolloClient
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

    override fun getGithubData(): Flow<ViewerQuery.Data> {
        return apolloClient.query(ViewerQuery()).toFlow().handleError()
    }

    override fun addReaction(
        subjectId: String,
        content: ReactionContent
    ): Flow<AddReactionMutation.Data> {
        return apolloClient.mutation(AddReactionMutation(subjectId, content)).toFlow().handleError()
    }

    override fun searchRepository(userName: String): Flow<SearchQuery.Data> {
        return apolloClient.query(SearchQuery(userName, 20)).toFlow().handleError()
    }
}
