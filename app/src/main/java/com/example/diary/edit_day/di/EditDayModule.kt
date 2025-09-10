package com.example.diary.edit_day.di

import com.example.diary.edit_day.presentation.EditDayViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val editDayModule = module {
    viewModelOf(::EditDayViewModel)
}