package com.example.android_architecture_base_flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_architecture_base_flow.util.InjectionUtil
import com.example.android_architecture_base_flow.view.ui.BaseViewModel
import com.example.android_architecture_base_flow.view.ui.Execution
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
