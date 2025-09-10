package com.example.diary.core.domain.repository

import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Day
import com.example.diary.core.domain.model.Food
import com.example.diary.core.domain.model.Toilet
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface Repository {
    fun getDays(): Flow<List<Day>>
    suspend fun getDay(dayId: Long): Day
    fun getAvailableFoods(): Flow<List<AvailableFood>>

    suspend fun insertDay(day: Day): Long
    suspend fun upsertFoods(foods: List<Food>)
    suspend fun upsertToilets(toilets: List<Toilet>)
    suspend fun upsertAvailableFoods(availableFoods: List<AvailableFood>)

    suspend fun deleteDay(day: Day)
    suspend fun deleteFoods(foods: List<Food>)
    suspend fun deleteToilets(toilets: List<Toilet>)
    suspend fun deleteAvailableFoods(availableFoods: List<AvailableFood>)

    fun getCurrentDate(): LocalDate
    suspend fun isDayExists(day: Day): Boolean
}