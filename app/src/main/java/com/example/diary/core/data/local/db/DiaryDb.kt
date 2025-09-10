package com.example.diary.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diary.core.data.local.dao.DiaryDao
import com.example.diary.core.data.local.entity.AvailableFoodEntity
import com.example.diary.core.data.local.entity.DayEntity
import com.example.diary.core.data.local.entity.FoodEntity
import com.example.diary.core.data.local.entity.ToiletEntity

@Database(
    entities = [
        DayEntity::class,
        FoodEntity::class,
        ToiletEntity::class,
        AvailableFoodEntity::class
    ],
    version = 1
)
abstract class DiaryDb : RoomDatabase() {
    abstract val diaryDao: DiaryDao
}