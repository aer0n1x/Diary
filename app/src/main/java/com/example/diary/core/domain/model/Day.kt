package com.example.diary.core.domain.model

import java.time.LocalDate

data class Day(
    val id: Long = 0,
    val date: LocalDate,
    val foods: List<Food> = emptyList(),
    val toilets: List<Toilet> = emptyList()
)