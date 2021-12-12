package com.example.apollo_android_github.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollo_android_github.util.InjectionUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel(
    private val injectionUtil: InjectionUtil
) : ViewModel() {

    private val _loading = MutableLiveData<Loading>()
    val loading: LiveData<Loading> = _loading

    private val _failure = MutableLiveData<Failure>()
    val failure: LiveData<Failure> = _failure

    fun <T> Flow<T>.execute(
        execution: Execution = DefaultExecution,
        onSuccess: ((T) -> Unit) = {},
        retry: () -> Unit
    ) {
        viewModelScope.launch {
            flowOn(injectionUtil.ioDispatcher)
                .onStart { _loading.value = Loading(execution, true) }
                .onCompletion { _loading.value = Loading(execution, false) }
                .catch {
                    _failure.value = Failure(execution, it, retry)
                    Timber.e(it)
                }
                .collect { onSuccess(it) }
        }
    }
}

interface Execution

object DefaultExecution : Execution

data class Loading(
    val exception: Execution,
    val isLoading: Boolean = false
)

data class Failure(
    val execution: Execution,
    val error: Throwable,
    val retry: () -> Unit
)
