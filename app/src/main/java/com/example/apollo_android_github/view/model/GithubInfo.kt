package com.example.apollo_android_github.view.model

import java.io.Serializable

data class GithubInfo(
    val user: String,
    val avatarUrl: String?,
    val repositories: List<GithubRepositoryInfo>
)

data class GithubRepositoryInfo(
    val repositoryName: String,
    val issues: List<Issue>
)

data class Issue(
    val id: String?,
    val title: String?,
    val url: String?
) : Serializable
