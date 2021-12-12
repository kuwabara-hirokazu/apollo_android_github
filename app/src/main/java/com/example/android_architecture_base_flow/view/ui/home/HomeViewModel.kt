package com.example.android_architecture_base_flow.view.ui.home

import com.example.android_architecture_base_flow.util.InjectionUtil
import com.example.android_architecture_base_flow.view.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    injectionUtil: InjectionUtil
) : BaseViewModel(injectionUtil)
