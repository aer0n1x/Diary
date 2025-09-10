package com.example.diary.core

import android.app.Application
import com.example.diary.core.di.coreModule
import com.example.diary.edit_day.di.editDayModule
import com.example.diary.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DiaryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(homeModule, editDayModule, coreModule)
            androidContext(this@DiaryApp)
        }
    }
}