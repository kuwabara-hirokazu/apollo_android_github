package com.example.apollo_android_github.data.repository

import com.apollographql.apollo3.annotations.ApolloExperimental
import com.example.apollo_android_github.AddReactionMutation
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.ViewerQuery
import com.example.apollo_android_github.await
import com.example.apollo_android_github.data.source.remote.GithubRemote
import com.example.apollo_android_github.givenForSuspend
import com.example.apollo_android_github.test.AddReactionMutation_TestBuilder.Data
import com.example.apollo_android_github.test.SearchQuery_TestBuilder.Data
import com.example.apollo_android_github.test.ViewerQuery_TestBuilder.Data
import com.example.apollo_android_github.type.ReactionContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn

@ApolloExperimental
@ExperimentalCoroutinesApi
class GithubRepositoryImplTest {

    companion object {
        // ViewerQuery
        private const val LOGIN = "test-user"
        private val VIEWER_DATA = ViewerQuery.Data {
            viewer = viewer { login = LOGIN }
        }

        // SearchQuery
        private val REPOSITORY_DATA = SearchQuery.Data {
            repositoryOwner = repositoryOwner {
                login = "test-user"
                avatarUrl = "url"
                repositories = repositories {
                    nodes = listOf(
                        node {
                            name = "repository1"
                            issues = issues {
                                nodes = listOf(
                                    node {
                                        id = "1"
                                        title = "issue1"
                                        url = "issueUrl"
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }

        // AddReactionMutation
        private const val SUBJECT_ID = "id"
        private val REACTION = ReactionContent.LAUGH
        private val ADD_REACTION_DATA = AddReactionMutation.Data {
            addReaction = addReaction {
                reaction = reaction { content = REACTION.rawValue }
                subject = subject { id = SUBJECT_ID }
            }
        }
    }

    @Mock
    private lateinit var remote: GithubRemote

    private lateinit var sut: GithubRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = GithubRepositoryImpl(remote)
    }

    @Test
    fun testGetGithubData() {
        // Given
        givenForSuspend(remote) {
            onBlocking { getGithubData() } doReturn flow { emit(VIEWER_DATA) }
        }

        givenForSuspend(remote) {
            onBlocking { searchRepository(LOGIN) } doReturn flow { emit(REPOSITORY_DATA) }
        }

        // When
        val response = sut.getGithubData().await()

        // Then
        assertEquals(REPOSITORY_DATA, response)
    }

    @Test
    fun testAddReaction() {
        // Given
        givenForSuspend(remote) {
            onBlocking { addReaction(SUBJECT_ID, REACTION) } doReturn flow { emit(ADD_REACTION_DATA) }
        }

        // When
        val response =
            sut.addReaction(SUBJECT_ID, REACTION).await()

        // Then
        assertEquals(ADD_REACTION_DATA, response)
    }
}
