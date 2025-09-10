@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.diary.edit_day.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Food
import com.example.diary.core.domain.model.Toilet
import com.example.diary.core.domain.model.ToiletLevel
import com.example.diary.core.presentation.theme.DiaryTheme
import com.example.diary.edit_day.presentation.components.FoodDialog
import com.example.diary.edit_day.presentation.components.FoodsSurface
import com.example.diary.edit_day.presentation.components.ToiletDialog
import com.example.diary.edit_day.presentation.components.ToiletsSurface
import java.time.LocalTime

@Composable
fun EditDayScreen(
    editDayViewModel: EditDayViewModel,
    dayId: Long
) {
    val state by editDayViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        editDayViewModel.onStart(dayId)
    }

    EditDayScreenRoot(
        state = state,
        onAction = editDayViewModel::onAction,
        navigateUp = editDayViewModel::navigateUp
    )
}

@Composable
private fun EditDayScreenRoot(
    state: EditDayState,
    onAction: (EditDayActions) -> Unit,
    navigateUp: () -> Unit
) {
    FoodDialog(
        isEnabled = state.foodDialogState,
        availableFoods = state.availableFoods,
        dayId = state.dayId,
        food = state.food,
        onAction = onAction
    )

    ToiletDialog(
        isEnabled = state.toiletDialogState,
        dayId = state.dayId,
        toilet = state.toilet,
        onAction = onAction
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Text("Редактирование дня")
            }, navigationIcon = {
                IconButton(
                    onClick = {
                        navigateUp()
                    }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    FoodsSurface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        foods = state.foods,
                        availableFoods = state.availableFoods,
                        onAction = onAction
                    )
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

                item {
                    ToiletsSurface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        toilets = state.toilets,
                        onAction = onAction
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        navigateUp()
                    }
                ) {
                    Text("Отмена")
                }
                Button(
                    onClick = {
                        onAction(EditDayActions.SaveDay)
                        navigateUp()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}

@PreviewDynamicColors
@Composable
private fun EditDayScreenPreview() {
    val foods = (1..5).map {
        Food(
            id = it.toLong(),
            foodId = it.toLong(),
            time = LocalTime.now(),
            dayId = 0
        )
    }
    val availableFoods = (1..5).map {
        AvailableFood(
            id = it.toLong(),
            name = "Печенья"
        )
    }
    val toilets = (1..5).map {
        Toilet(
            id = it.toLong(),
            toiletLevel = ToiletLevel.SMOOTH_SOFT_SAUSAGE,
            time = LocalTime.now(),
            dayId = 0
        )
    }
    val state = EditDayState(
        foods = foods,
        toilets = toilets,
        availableFoods = availableFoods
    )

    DiaryTheme(darkTheme = true) {
        EditDayScreenRoot(
            state = state,
            onAction = {},
            navigateUp = {}
        )
    }
}