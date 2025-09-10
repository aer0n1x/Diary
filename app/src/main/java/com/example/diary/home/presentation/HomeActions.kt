package com.example.diary.home.presentation

import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Day

sealed interface HomeActions {
    object onFabButtonClick : HomeActions
    data class AddAvailableFood(val inputText: String) : HomeActions
    data class EditAvailableFood(val availableFood: AvailableFood) : HomeActions

    data class DeleteDay(val day: Day) : HomeActions
    data class DeleteAvailableFood(val availableFood: AvailableFood) : HomeActions
    data class ShowAvailableFoodDialog(val availableFood: AvailableFood? = null) : HomeActions
    object CloseAvailableFoodDialog : HomeActions

    data class ValidateAddAvailableFoodDialog(val inputText: String) : HomeActions
}
