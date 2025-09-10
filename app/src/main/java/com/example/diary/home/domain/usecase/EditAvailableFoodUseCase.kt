package com.example.diary.home.domain.usecase

import com.example.diary.core.domain.error.onSuccess
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.repository.Repository
import com.example.diary.home.domain.usecase.format.TextFormatter
import com.example.diary.home.domain.usecase.validate.AvailableFoodValidator

class EditAvailableFoodUseCase(
    private val repository: Repository,
    private val availableFoodValidator: AvailableFoodValidator,
    private val textFormatter: TextFormatter
) {
    suspend operator fun invoke(availableFood: AvailableFood) {
        textFormatter(availableFood.name).onSuccess { formattedText ->
            availableFoodValidator(formattedText).onSuccess {
                repository.upsertAvailableFoods(
                    listOf(
                        availableFood.copy(
                            name = formattedText
                        )
                    )
                )
            }
        }
    }
}