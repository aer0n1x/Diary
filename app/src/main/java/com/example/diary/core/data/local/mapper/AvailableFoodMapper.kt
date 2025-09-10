package com.example.diary.core.data.local.mapper

import com.example.diary.core.data.local.entity.AvailableFoodEntity
import com.example.diary.core.domain.model.AvailableFood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

private fun AvailableFoodEntity.toAvailableFood(): AvailableFood {
    return AvailableFood(
        id = id,
        name = name
    )
}

fun AvailableFood.toAvailableFoodEntity(): AvailableFoodEntity {
    return AvailableFoodEntity(
        id = id,
        name = name
    )
}

fun Flow<List<AvailableFoodEntity>>.toAvailableFood(): Flow<List<AvailableFood>> {
    return map { availableFoodEntity -> availableFoodEntity.map { availableFood -> availableFood.toAvailableFood() } }
}

fun List<AvailableFood>.toAvailableFoodEntity(): List<AvailableFoodEntity> {
    return map { it.toAvailableFoodEntity() }
}