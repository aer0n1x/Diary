package com.example.diary.core.data.local.repository

import com.example.diary.core.data.local.dao.DiaryDao
import com.example.diary.core.data.local.mapper.toAvailableFood
import com.example.diary.core.data.local.mapper.toAvailableFoodEntity
import com.example.diary.core.data.local.mapper.toDay
import com.example.diary.core.data.local.mapper.toDayEntity
import com.example.diary.core.data.local.mapper.toFoodEntity
import com.example.diary.core.data.local.mapper.toToiletEntity
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Day
import com.example.diary.core.domain.model.Food
import com.example.diary.core.domain.model.Toilet
import com.example.diary.core.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class RepositoryImpl(
    private val dao: DiaryDao
) : Repository {
    override fun getDays(): Flow<List<Day>> {
        return dao.getDays().toDay()
    }

    override suspend fun getDay(dayId: Long): Day {
        return dao.getDay(dayId).toDay()
    }

    override fun getAvailableFoods(): Flow<List<AvailableFood>> {
        return dao.getAvailableFoods().toAvailableFood()
    }

    override suspend fun insertDay(day: Day): Long {
        return dao.insertDay(day.toDayEntity())
    }

    override suspend fun upsertFoods(foods: List<Food>) {
        dao.upsertFoods(foods.toFoodEntity())
    }

    override suspend fun upsertToilets(toilets: List<Toilet>) {
        dao.upsertToilets(toilets.toToiletEntity())
    }

    override suspend fun upsertAvailableFoods(availableFoods: List<AvailableFood>) {
        dao.upsertAvailableFoods(availableFoods.toAvailableFoodEntity())
    }

    override suspend fun deleteDay(day: Day) {
        dao.deleteDay(day.toDayEntity())
    }

    override suspend fun deleteFoods(foods: List<Food>) {
        dao.deleteFoods(foods.toFoodEntity())
    }

    override suspend fun deleteToilets(toilets: List<Toilet>) {
        dao.deleteToilets(toilets.toToiletEntity())
    }

    override suspend fun deleteAvailableFoods(availableFoods: List<AvailableFood>) {
        dao.deleteAvailableFoods(availableFoods.toAvailableFoodEntity())
    }

    override fun getCurrentDate(): LocalDate {
        return LocalDate.now()
    }

    override suspend fun isDayExists(day: Day): Boolean {
        val existingDays = getDays().first()

        return existingDays.any { it.date == day.date }
    }
}