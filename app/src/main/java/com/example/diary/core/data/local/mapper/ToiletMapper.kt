package com.example.diary.core.data.local.mapper

import com.example.diary.core.data.local.entity.ToiletEntity
import com.example.diary.core.domain.model.Toilet
import java.time.LocalTime

fun ToiletEntity.toToilet(): Toilet {
    return Toilet(
        id = id,
        toiletLevel = toiletLevel,
        time = LocalTime.parse(time),
        dayId = dayId
    )
}
fun Toilet.toToiletEntity(): ToiletEntity {
    return ToiletEntity(
        id = id,
        toiletLevel = toiletLevel,
        time = time.toString(),
        dayId = dayId
    )
}

fun List<Toilet>.toToiletEntity(): List<ToiletEntity> {
    return map { it.toToiletEntity() }
}