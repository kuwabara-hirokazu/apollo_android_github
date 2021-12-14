package com.example.apollo_android_github.view.ui.webview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apollo_android_github.data.repository.GithubRepository
import com.example.apollo_android_github.type.ReactionContent
import com.example.apollo_android_github.util.InjectionUtil
import com.example.apollo_android_github.util.Signal
import com.example.apollo_android_github.view.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GithubWebViewViewModel @Inject constructor(
    private val repository: GithubRepository,
    injectionUtil: InjectionUtil
) : BaseViewModel(injectionUtil) {

    private val _saved = MutableLiveData<Signal>()
    val saved: LiveData<Signal> = _saved

    fun addReaction(subjectId: String) {
        val reaction = ReactionContent.values()[Random.nextInt(8)]
        repository.addReaction(subjectId, reaction)
            .execute(
                onSuccess = { _saved.postValue(Signal) },
                retry = { addReaction(subjectId) }
            )
    }
}
