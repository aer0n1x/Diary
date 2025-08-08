package com.example.diary.home.domain.model

data class Day(
    val id: Int,
    val date: String,
    val food: Food,
    val toilet: Toilet
)