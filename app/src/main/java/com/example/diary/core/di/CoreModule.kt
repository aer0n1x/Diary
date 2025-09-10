package com.example.diary.core.di

import com.example.diary.core.presentation.navigation.DefaultNavigator
import com.example.diary.core.presentation.navigation.Destination
import com.example.diary.core.presentation.navigation.Navigator
import org.koin.dsl.module

val coreModule = module {
    single<Navigator> { DefaultNavigator(startDestination = Destination.HomeScreen) }
}