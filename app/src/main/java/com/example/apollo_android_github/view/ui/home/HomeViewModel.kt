package com.example.apollo_android_github.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apollo_android_github.data.repository.GithubRepository
import com.example.apollo_android_github.ext.map
import com.example.apollo_android_github.util.InjectionUtil
import com.example.apollo_android_github.view.mapper.GithubInfoMapper
import com.example.apollo_android_github.view.model.GithubInfo
import com.example.apollo_android_github.view.model.GithubRepositoryInfo
import com.example.apollo_android_github.view.model.Issue
import com.example.apollo_android_github.view.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GithubRepository,
    private val mapper: GithubInfoMapper,
    injectionUtil: InjectionUtil
) : BaseViewModel(injectionUtil) {

    private val _githubInfo = MutableLiveData<GithubInfo>()
    val githubInfo: LiveData<GithubInfo> = _githubInfo

    fun getRepositoryList(): List<GithubRepositoryInfo> {
        return _githubInfo.value?.repositories ?: listOf()
    }

    fun getIssueList(position: Int): List<Issue> {
        return getRepositoryList()[position].issues
    }

    fun getGithubInfo() {
        repository.getGithubData()
            .map(mapper)
            .execute(
                onSuccess = { _githubInfo.value = it },
                retry = { getGithubInfo() }
            )
    }
}
