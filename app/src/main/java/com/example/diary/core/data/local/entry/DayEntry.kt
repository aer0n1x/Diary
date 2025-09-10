package com.example.diary.core.data.local.entry

import androidx.room.Embedded
import androidx.room.Relation
import com.example.diary.core.data.local.entity.DayEntity
import com.example.diary.core.data.local.entity.FoodEntity
import com.example.diary.core.data.local.entity.ToiletEntity

data class DayEntry(
    @Embedded
    val day: DayEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "dayId"
    )
    val foods: List<FoodEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "dayId"
    )
    val toilets: List<ToiletEntity>
)
