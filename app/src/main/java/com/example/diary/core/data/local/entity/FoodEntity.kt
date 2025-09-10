package com.example.diary.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
        entity = DayEntity::class,
        parentColumns = ["id"],
        childColumns = ["dayId"],
        onDelete = CASCADE
        ),
        ForeignKey(
        entity = AvailableFoodEntity::class,
        parentColumns = ["id"],
        childColumns = ["foodId"],
        onDelete = CASCADE
    )],
    indices = [
        Index("dayId"),
        Index("foodId")
    ]
)
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val time: String,
    val foodId: Long,
    val dayId: Long
)
