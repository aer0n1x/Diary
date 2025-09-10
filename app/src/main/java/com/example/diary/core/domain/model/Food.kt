package com.example.diary.core.domain.model

import java.time.LocalTime

data class Food(
    val id: Long = 0,
    val foodId: Long,
    val time: LocalTime,
    val dayId: Long = 0
)
