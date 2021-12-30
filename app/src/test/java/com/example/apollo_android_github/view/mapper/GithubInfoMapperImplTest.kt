package com.example.apollo_android_github.view.mapper

import com.apollographql.apollo3.annotations.ApolloExperimental
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.test.SearchQuery_TestBuilder.Data
import com.example.apollo_android_github.view.model.GithubInfo
import com.example.apollo_android_github.view.model.GithubRepositoryInfo
import com.example.apollo_android_github.view.model.Issue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ApolloExperimental
class GithubInfoMapperImplTest {
    companion object {
        // Map前
        private val ENTITY = SearchQuery.Data {
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

        // Map後
        private val MODEL_ISSUE = Issue("1", "issue1", "issueUrl")
        private val MODEL_REPOSITORY = GithubRepositoryInfo("repository1", listOf(MODEL_ISSUE))
        private val MODEL = GithubInfo("test-user", "url", listOf(MODEL_REPOSITORY))
    }

    private lateinit var sut: GithubInfoMapperImpl

    @Before
    fun setUp() {
        sut = GithubInfoMapperImpl()
    }

    @Test
    fun testMap() {
        // Given

        // When
        val result = sut.map(ENTITY)

        // Then
        assertEquals(MODEL, result)
    }
}
