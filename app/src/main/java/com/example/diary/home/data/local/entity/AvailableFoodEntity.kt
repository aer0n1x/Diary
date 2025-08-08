package com.example.diary.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AvailableFoodEntity(
    @PrimaryKey val id: Int? = null,
    val name: String
)