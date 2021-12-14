package com.example.apollo_android_github.ext

import com.example.apollo_android_github.view.mapper.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <ENTITY, MODEL> Flow<ENTITY>.map(mapper: Mapper<ENTITY, MODEL>): Flow<MODEL> {
    return map { entity ->
        mapper.map(entity)
    }
}
