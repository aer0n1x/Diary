package com.example.diary.edit_day.presentation

import com.example.diary.core.domain.model.Food
import com.example.diary.core.domain.model.Toilet

sealed interface EditDayActions {
    data class ShowFoodDialog(val food: Food? = null) : EditDayActions
    data class ShowToiletDialog(val toilet: Toilet? = null) : EditDayActions

    object CloseAddFoodDialog : EditDayActions
    object CloseAddToiletDialog : EditDayActions

    data class AddFood(val food: Food): EditDayActions
    data class EditFood(val food: Food): EditDayActions
    data class AddToilet(val toilet: Toilet): EditDayActions
    data class EditToilet(val toilet: Toilet): EditDayActions

    data class DeleteFood(val food: Food): EditDayActions
    data class DeleteToilet(val toilet: Toilet): EditDayActions
    object SaveDay: EditDayActions
}