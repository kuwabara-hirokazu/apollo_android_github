package com.example.apollo_android_github.view.mapper

import com.example.apollo_android_github.SearchQuery
import com.example.apollo_android_github.view.model.GithubInfo
import com.example.apollo_android_github.view.model.GithubRepositoryInfo
import com.example.apollo_android_github.view.model.Issue
import javax.inject.Inject

typealias GithubInfoMapper = Mapper<SearchQuery.Data, GithubInfo>

class GithubInfoMapperImpl @Inject constructor() : GithubInfoMapper {
    override fun map(entity: SearchQuery.Data): GithubInfo {
        val owner = entity.repositoryOwner
        val repositoryList = entity.repositoryOwner?.repositories?.nodes ?: listOf()

        return GithubInfo(
            user = owner?.login ?: "",
            avatarUrl = owner?.avatarUrl as? String,
            repositories = repositoryList.map {
                val issues = it?.searchResultRepository?.issues?.nodes ?: listOf()
                GithubRepositoryInfo(
                    repositoryName = it?.searchResultRepository?.name ?: "",
                    issues = issues.map { repository ->
                        Issue(
                            id = repository?.id,
                            title = repository?.title,
                            url = repository?.url as? String
                        )
                    }
                )
            }
        )
    }
}
