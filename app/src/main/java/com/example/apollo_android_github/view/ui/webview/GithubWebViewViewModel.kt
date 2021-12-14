package com.example.apollo_android_github.view.ui.webview

import com.example.apollo_android_github.data.repository.GithubRepository
import com.example.apollo_android_github.util.InjectionUtil
import com.example.apollo_android_github.view.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GithubWebViewViewModel @Inject constructor(
    private val repository: GithubRepository,
    injectionUtil: InjectionUtil
) : BaseViewModel(injectionUtil)
