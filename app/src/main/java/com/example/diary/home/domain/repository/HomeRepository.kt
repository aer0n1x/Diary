package com.example.diary.home.domain.repository

import com.example.diary.home.domain.model.Day

interface HomeRepository {
    suspend fun getDay(id: Int): Day

    suspend fun addDay(day: Day)
}