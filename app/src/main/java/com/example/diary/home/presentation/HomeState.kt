package com.example.diary.home.presentation

import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Day

data class HomeState(
    val days: List<Day> = emptyList(),
    val availableFoods: List<AvailableFood> = emptyList(),
    val availableFoodDialogState: Boolean = false,
    val availableFoodDialogValidationState: Boolean = false,
    val availableFood: AvailableFood? = null,
    val recentlyDeleted: RecentlyDeleted? = null
)
