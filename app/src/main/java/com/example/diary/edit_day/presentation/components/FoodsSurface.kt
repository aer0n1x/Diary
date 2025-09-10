package com.example.diary.edit_day.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.dp
import com.example.diary.core.presentation.theme.DiaryTheme
import com.example.diary.edit_day.presentation.EditDayActions
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Food
import java.time.LocalTime

@Composable
fun FoodsSurface(
    modifier: Modifier = Modifier,
    color: Color,
    foods: List<Food>,
    availableFoods: List<AvailableFood>,
    onAction: (EditDayActions) -> Unit
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Еда",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd), onClick = {
                        onAction(EditDayActions.ShowFoodDialog())
                    }) {
                    Icon(Icons.Default.Add, null)
                }
            }
            foods.forEach { food ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    onClick = {
                        onAction(EditDayActions.ShowFoodDialog(food))
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var foodName = ""
                        if (availableFoods.isNotEmpty()) {
                            availableFoods.forEach { availableFood ->
                                if (availableFood.id == food.foodId) {
                                    foodName = "${availableFood.name} ${food.time}"
                                }
                            }
                        }
                        Text(foodName)
                        IconButton(
                            onClick = {
                                onAction(EditDayActions.DeleteFood(food))
                            }) {
                            Icon(Icons.Default.Delete, null)
                        }
                    }
                }
            }
        }
    }
}

@PreviewDynamicColors
@Composable
private fun FoodsSurfacePreview() {
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

    DiaryTheme(darkTheme = true) {
        FoodsSurface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            foods = foods,
            availableFoods = availableFoods,
            onAction = {}
        )
    }
}