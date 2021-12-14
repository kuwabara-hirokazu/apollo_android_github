package com.example.apollo_android_github.view.mapper

interface Mapper<ENTITY, MODEL> {
    fun map(entity: ENTITY): MODEL
}
