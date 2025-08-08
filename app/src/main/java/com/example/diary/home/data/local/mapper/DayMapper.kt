package com.example.diary.home.data.local.mapper

import com.example.diary.home.data.local.entity.DayEntity
import com.example.diary.home.domain.model.Day

fun DayEntity.toDay(): Day {
    return Day(
        id = id ?: 0, date = date, food = food, toilet = toilet
    )
}

fun Day.toDayEntity(): DayEntity {
    return DayEntity(
        id = id,
        date = date,
        food = food,
        toilet = toilet
    )
}