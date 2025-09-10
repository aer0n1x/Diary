package com.example.diary.edit_day.presentation

import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Food
import com.example.diary.core.domain.model.Toilet

data class EditDayState(
    val foodDialogState: Boolean = false,
    val toiletDialogState: Boolean = false,
    val foods: List<Food> = emptyList(),
    val toilets: List<Toilet> = emptyList(),
    val oldFoods: List<Food> = emptyList(),
    val oldToilets: List<Toilet> = emptyList(),
    val availableFoods: List<AvailableFood> = emptyList(),
    val dayId: Long = 0,
    val food: Food? = null,
    val toilet: Toilet? = null
)
