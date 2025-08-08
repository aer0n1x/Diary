package com.example.diary.home.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diary.home.data.local.dao.DiaryDao
import com.example.diary.home.data.local.entity.AvailableFoodEntity
import com.example.diary.home.data.local.entity.DayEntity

@Database(
    entities = [DayEntity::class, AvailableFoodEntity::class],
    version = 1
)
abstract class DiaryDb : RoomDatabase() {
    abstract val diaryDao: DiaryDao

    companion object {
        const val DB_NAME = "diary.db"
    }
}