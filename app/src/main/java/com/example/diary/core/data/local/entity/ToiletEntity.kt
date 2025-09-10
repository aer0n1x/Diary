package com.example.diary.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.diary.core.domain.model.ToiletLevel

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = DayEntity::class,
            parentColumns = ["id"],
            childColumns = ["dayId"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("dayId")
    ]
)
data class ToiletEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val toiletLevel: ToiletLevel,
    val time: String,
    val dayId: Long
)
