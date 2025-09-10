package com.example.diary.core.data.local.mapper

import com.example.diary.core.data.local.entity.FoodEntity
import com.example.diary.core.domain.model.Food
import java.time.LocalTime

fun FoodEntity.toFood(): Food {
    return Food(
        id = id,
        foodId = foodId,
        time = LocalTime.parse(time),
        dayId = dayId
    )
}

fun Food.toFoodEntity(): FoodEntity {
    return FoodEntity(
        id = id,
        time = time.toString(),
        foodId = foodId,
        dayId = dayId
    )
}

fun List<Food>.toFoodEntity(): List<FoodEntity> {
    return map { it.toFoodEntity() }
}