package com.example.apollo_android_github.di

import com.example.apollo_android_github.view.mapper.GithubInfoMapper
import com.example.apollo_android_github.view.mapper.GithubInfoMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Singleton
    @Binds
    abstract fun bindGithubInfoMapper(
        githubInfoMapper: GithubInfoMapperImpl
    ): GithubInfoMapper
}
