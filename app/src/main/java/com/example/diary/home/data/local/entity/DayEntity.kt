package com.example.diary.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diary.home.domain.model.Food
import com.example.diary.home.domain.model.Toilet

@Entity
data class DayEntity(
    @PrimaryKey val id: Int? = null,
    val date: String,
    val food: Food,
    val toilet: Toilet
)