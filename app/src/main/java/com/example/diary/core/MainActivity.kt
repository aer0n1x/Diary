package com.example.diary.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.diary.core.presentation.ObserveAsEvents
import com.example.diary.core.presentation.navigation.Destination
import com.example.diary.core.presentation.navigation.NavigationAction
import com.example.diary.core.presentation.navigation.Navigator
import com.example.diary.core.presentation.theme.DiaryTheme
import com.example.diary.edit_day.presentation.EditDayScreen
import com.example.diary.home.presentation.HomeScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navigator = koinInject<Navigator>()

            ObserveAsEvents(navigator.navigationActions) { action ->
                when (action) {
                    is NavigationAction.Navigate -> {
                        navController.navigate(action.destination) {
                            action.navOptions(this)
                        }
                    }
                    NavigationAction.NavigateUp -> navController.navigateUp()
                }
            }

            DiaryTheme {
                NavHost(
                    navController = navController,
                    startDestination = navigator.startDestination
                ) {
                    composable<Destination.HomeScreen> {
                        HomeScreen(koinViewModel())
                    }
                    composable<Destination.EditDayScreen> {
                        val args = it.toRoute<Destination.EditDayScreen>()

                        EditDayScreen(koinViewModel(), args.dayId)
                    }
                }
            }
        }
    }
}