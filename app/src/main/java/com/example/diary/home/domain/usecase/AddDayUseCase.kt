package com.example.diary.home.domain.usecase

import com.example.diary.core.domain.model.Day
import com.example.diary.core.domain.repository.Repository

class AddDayUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(day: Day): Long? {
        val isDayExists = repository.isDayExists(day)
        if (isDayExists) return null

        return repository.insertDay(day)
    }
}