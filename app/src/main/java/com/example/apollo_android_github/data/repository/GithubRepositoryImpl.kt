package com.example.apollo_android_github.data.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.example.apollo_android_github.AddReactionMutation
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.data.source.remote.GithubRemote
import com.example.apollo_android_github.type.ReactionContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GithubRepositoryImpl @Inject constructor(
    private val remote: GithubRemote
) : GithubRepository {
    override fun getGithubData(): Flow<ApolloResponse<SearchQuery.Data>> {
        return remote.getGithubData()
            .flatMapLatest {
                val name = it.data?.viewer?.login ?: ""
                remote.searchRepository(name)
            }
    }

    override fun addReaction(
        subjectId: String,
        content: ReactionContent
    ): Flow<ApolloResponse<AddReactionMutation.Data>> {
        return remote.addReaction(subjectId, content)
    }
}
