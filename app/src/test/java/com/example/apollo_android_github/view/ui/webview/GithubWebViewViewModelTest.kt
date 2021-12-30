package com.example.apollo_android_github.view.ui.webview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.example.apollo_android_github.AddReactionMutation
import com.example.apollo_android_github.CoroutinesTestRule
import com.example.apollo_android_github.data.repository.GithubRepository
import com.example.apollo_android_github.test.AddReactionMutation_TestBuilder.Data
import com.example.apollo_android_github.testObserver
import com.example.apollo_android_github.type.ReactionContent
import com.example.apollo_android_github.util.InjectionUtil
import com.example.apollo_android_github.util.Signal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ApolloExperimental
@ExperimentalCoroutinesApi
class GithubWebViewViewModelTest {
    companion object {
        private const val SUBJECT_ID = "id"
        private val REACTION = ReactionContent.LAUGH
        private val DATA = AddReactionMutation.Data {
            addReaction = addReaction {
                reaction = reaction { content = REACTION.rawValue }
                subject = subject { id = SUBJECT_ID }
            }
        }
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = CoroutinesTestRule()

    @Mock
    private lateinit var repository: GithubRepository

    private lateinit var sut: GithubWebViewViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = GithubWebViewViewModel(repository, InjectionUtil(coroutinesRule.testDispatcher))
    }

    @Test
    fun testAddReactionSuccess() {
        // Given
        BDDMockito.given(repository.addReaction(SUBJECT_ID, REACTION))
            .willReturn(flow { emit(DATA) })
        val observer = sut.saved.testObserver()

        // When
        sut.addReaction(SUBJECT_ID, REACTION)

        // Then
        observer
            .await(1)
            .shouldReceive(Signal)
            .end()
    }
}
