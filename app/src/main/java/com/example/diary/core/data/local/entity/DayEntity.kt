package com.example.diary.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String
)