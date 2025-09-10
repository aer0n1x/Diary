@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.diary.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.diary.R
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Day
import com.example.diary.core.presentation.theme.DiaryTheme
import com.example.diary.home.presentation.components.AvailableFoodDialog
import com.example.diary.home.presentation.components.AvailableFoodsSurface
import com.example.diary.home.presentation.components.DaysSurface
import java.time.LocalDate

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {
    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _,event ->
            if (event == Lifecycle.Event.ON_STOP) {
                homeViewModel.snackbarHostState.currentSnackbarData?.dismiss()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    HomeScreenRoot(
        state = state,
        snackbarHostState = homeViewModel.snackbarHostState,
        onAction = homeViewModel::onAction,
        homeViewModel::navigateToEditDay
    )
}

@Composable
private fun HomeScreenRoot(
    state: HomeState,
    snackbarHostState: SnackbarHostState,
    onAction: (HomeActions) -> Unit = {},
    navigateToEditDay: (Long) -> Unit
) {
    AvailableFoodDialog(
        isEnabled = state.availableFoodDialogState,
        isButtonEnabled = state.availableFoodDialogValidationState,
        availableFood = state.availableFood,
        onAction = onAction
    )

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.app_name))
            })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                onAction(HomeActions.onFabButtonClick)
            }
        ) {
            Icon(Icons.Default.Edit, contentDescription = null)
        }
    }, snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                DaysSurface(
                    modifier = Modifier.fillMaxWidth(),
                    days = state.days,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    onAction = onAction,
                    onNavigate = navigateToEditDay,
                    recentlyDeleted = state.recentlyDeleted as? RecentlyDeleted.DayUndo
                )
            }
            item {
                HorizontalDivider(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            item {
                AvailableFoodsSurface(
                    modifier = Modifier.fillMaxWidth(),
                    availableFoods = state.availableFoods,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    onAction = onAction,
                    recentlyDeleted = state.recentlyDeleted as? RecentlyDeleted.AvailableFoodUndo
                )
            }
        }
    }
}

@PreviewDynamicColors
@Composable
private fun HomeScreenPreview() {
    val days = (1..5).map {
        Day(
            id = it.toLong(), date = LocalDate.of(2025, 7, it), foods = emptyList(), toilets = emptyList()
        )
    }

    val availableFoods = (1..5).map {
        AvailableFood(
            id = it.toLong(),
            name = "Печенья"
        )
    }

    val state = HomeState(
        days = days, availableFoods = availableFoods
    )

    DiaryTheme(darkTheme = true) {
        HomeScreenRoot(state = state, snackbarHostState = SnackbarHostState(), onAction = {}, navigateToEditDay = {})
    }
}