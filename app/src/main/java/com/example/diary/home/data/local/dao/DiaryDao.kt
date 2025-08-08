package com.example.diary.home.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.diary.home.data.local.entity.DayEntity

@Dao
interface DiaryDao {
    @Query("SELECT * FROM DayEntity")
    suspend fun getDay(id: Int): DayEntity

    @Upsert
    suspend fun addDay(day: DayEntity)
}