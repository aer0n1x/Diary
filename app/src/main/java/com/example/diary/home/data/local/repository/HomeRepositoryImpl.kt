package com.example.diary.home.data.local.repository

import com.example.diary.home.data.local.dao.DiaryDao
import com.example.diary.home.data.local.mapper.toDay
import com.example.diary.home.data.local.mapper.toDayEntity
import com.example.diary.home.domain.model.Day
import com.example.diary.home.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val dao: DiaryDao
) : HomeRepository {
    override suspend fun getDay(id: Int): Day {
        return dao.getDay(id).toDay()
    }

    override suspend fun addDay(day: Day) {
        dao.addDay(day.toDayEntity())
    }
}