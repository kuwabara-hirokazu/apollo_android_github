package com.example.apollo_android_github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apollo_android_github.util.InjectionUtil
import com.example.apollo_android_github.view.ui.BaseViewModel
import com.example.apollo_android_github.view.ui.Execution
import kotlinx.coroutines.flow.flow
import java.io.IOException

class TestViewModel(
    private val execution: Execution,
    injectionUtil: InjectionUtil
) : BaseViewModel(injectionUtil) {

    private val _testData = MutableLiveData<Int>()
    val testData: LiveData<Int>
        get() = _testData

    fun fetchDataWithFlow(): Unit =
        flow {
            emit(1)
        }.execute(execution, { _testData.value = it }, { fetchDataWithFlow() })

    fun throwException(): Unit =
        flow {
            throw IOException()
            emit(1)
        }.execute(execution, { _testData.value = it }, { })
}
