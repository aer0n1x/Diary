package com.example.diary.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.diary.core.data.local.entity.AvailableFoodEntity
import com.example.diary.core.data.local.entity.DayEntity
import com.example.diary.core.data.local.entity.FoodEntity
import com.example.diary.core.data.local.entity.ToiletEntity
import com.example.diary.core.data.local.entry.DayEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Transaction
    @Query("SELECT * FROM DayEntity ORDER BY id DESC")
    fun getDays(): Flow<List<DayEntry>>

    @Transaction
    @Query("SELECT * FROM DayEntity WHERE id = :dayId")
    suspend fun getDay(dayId: Long): DayEntry

    @Query("SELECT * FROM AvailableFoodEntity ORDER BY id DESC")
    fun getAvailableFoods(): Flow<List<AvailableFoodEntity>>

    @Delete
    suspend fun deleteDay(day: DayEntity)

    @Delete
    suspend fun deleteFoods(foods: List<FoodEntity>)

    @Delete
    suspend fun deleteToilets(toilets: List<ToiletEntity>)

    @Delete
    suspend fun deleteAvailableFoods(availableFoods: List<AvailableFoodEntity>)

    @Insert
    suspend fun insertDay(day: DayEntity): Long

    @Upsert
    suspend fun upsertFoods(foods: List<FoodEntity>)

    @Upsert
    suspend fun upsertToilets(toilets: List<ToiletEntity>)

    @Upsert
    suspend fun upsertAvailableFoods(availableFoods: List<AvailableFoodEntity>)
}