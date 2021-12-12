package com.example.apollo_android_github.view.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.apollo_android_github.CoroutinesTestRule
import com.example.apollo_android_github.TestViewModel
import com.example.apollo_android_github.testObserver
import com.example.apollo_android_github.util.InjectionUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BaseViewModelTest {

    // LiveDataをテストするために必要
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = CoroutinesTestRule()

    private lateinit var sut: TestViewModel

    @Before
    fun setUp() {
        sut = TestViewModel(DefaultExecution, InjectionUtil(coroutinesRule.testDispatcher))
    }

    @Test
    fun testExecuteSuccess() {
        // Given
        val observer = sut.testData.testObserver()

        // When
        sut.fetchDataWithFlow()

        // Then
        observer
            .await(1)
            .shouldReceive(1)
            .withValueCount(1)
            .end()
    }

    @Test
    fun testExecuteFailure() {
        // Given
        val observer = sut.failure.testObserver()

        // When
        sut.throwException()

        // Then
        observer
            .await(1)
            .withValueCount(1)
            .end()
    }
}
