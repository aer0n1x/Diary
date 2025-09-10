package com.example.diary.core.data.local.mapper

import com.example.diary.core.data.local.entity.DayEntity
import com.example.diary.core.data.local.entry.DayEntry
import com.example.diary.core.domain.model.Day
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

fun DayEntry.toDay(): Day {
    return Day(
        id = day.id,
        date = LocalDate.parse(day.date),
        foods = foods.map { it.toFood() },
        toilets = toilets.map { it.toToilet() })
}

fun Day.toDayEntity(): DayEntity {
    return DayEntity(
        id = id, date = date.toString()
    )
}

fun Flow<List<DayEntry>>.toDay(): Flow<List<Day>> {
    return map { dayEntity -> dayEntity.map { day -> day.toDay() } }
}