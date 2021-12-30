package com.example.apollo_android_github.view.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.example.apollo_android_github.CoroutinesTestRule
import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.data.repository.GithubRepository
import com.example.apollo_android_github.test.SearchQuery_TestBuilder.Data
import com.example.apollo_android_github.testObserver
import com.example.apollo_android_github.util.InjectionUtil
import com.example.apollo_android_github.view.mapper.GithubInfoMapper
import com.example.apollo_android_github.view.model.GithubInfo
import com.example.apollo_android_github.view.model.GithubRepositoryInfo
import com.example.apollo_android_github.view.model.Issue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@ApolloExperimental
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    companion object {
        private val GITHUB_ISSUE = Issue("1", "issue1", "issueUrl")
        private val GITHUB_REPOSITORY_INFO =
            GithubRepositoryInfo("repository1", listOf(GITHUB_ISSUE))
        private val GITHUB_INFO = GithubInfo("test-user", "url", listOf(GITHUB_REPOSITORY_INFO))

        private val DATA = SearchQuery.Data {
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
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = CoroutinesTestRule()

    @Mock
    private lateinit var repository: GithubRepository

    @Mock
    private lateinit var mapper: GithubInfoMapper

    private lateinit var sut: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = HomeViewModel(repository, mapper, InjectionUtil(coroutinesRule.testDispatcher))
    }

    @Test
    fun testGetGithubInfoSuccess() {
        // Given
        given(repository.getGithubData()).willReturn(flow { emit(DATA) })
        given(mapper.map(DATA)).willReturn(GITHUB_INFO)
        val observer = sut.githubInfo.testObserver()

        // When
        sut.getGithubInfo()

        // Then
        observer
            .await(1)
            .shouldReceive(GITHUB_INFO)
            .end()
    }
}
