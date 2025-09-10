package com.example.diary.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data class EditDayScreen(val dayId: Long) : Destination

    @Serializable
    object HomeScreen : Destination
}