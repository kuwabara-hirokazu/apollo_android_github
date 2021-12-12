package com.example.apollo_android_github.view.ui.home

import com.example.apollo_android_github.util.InjectionUtil
import com.example.apollo_android_github.view.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    injectionUtil: InjectionUtil
) : BaseViewModel(injectionUtil)
