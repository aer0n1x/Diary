package com.example.diary.home.di

import androidx.room.Room
import com.example.diary.core.data.local.db.DiaryDb
import com.example.diary.core.data.local.repository.RepositoryImpl
import com.example.diary.core.domain.repository.Repository
import com.example.diary.home.domain.usecase.AddAvailableFoodUseCase
import com.example.diary.home.domain.usecase.AddDayUseCase
import com.example.diary.home.domain.usecase.EditAvailableFoodUseCase
import com.example.diary.home.domain.usecase.format.FormatTextUseCase
import com.example.diary.home.domain.usecase.format.TextFormatter
import com.example.diary.home.domain.usecase.validate.AvailableFoodValidateUseCase
import com.example.diary.home.domain.usecase.validate.AvailableFoodValidator
import com.example.diary.home.presentation.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = DiaryDb::class.java,
            name = "Diary.db"
        ).build()
    }

    singleOf(::RepositoryImpl).bind<Repository>()

    single { get<DiaryDb>().diaryDao }

    singleOf(::FormatTextUseCase)
    singleOf(::AvailableFoodValidateUseCase)
    singleOf(::AddDayUseCase)
    singleOf(::AddAvailableFoodUseCase)
    singleOf(::EditAvailableFoodUseCase)
    singleOf(::AvailableFoodValidateUseCase).bind<AvailableFoodValidator>()
    singleOf(::FormatTextUseCase).bind<TextFormatter>()

    viewModelOf(::HomeViewModel)
}