package com.example.diary.home.presentation

import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Day

sealed interface RecentlyDeleted {
    data class DayUndo(val day: Day) : RecentlyDeleted
    data class AvailableFoodUndo(val availableFood: AvailableFood) : RecentlyDeleted
}