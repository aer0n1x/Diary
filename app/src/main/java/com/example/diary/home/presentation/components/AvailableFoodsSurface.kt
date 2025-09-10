package com.example.diary.home.presentation.components

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
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.home.presentation.HomeActions
import com.example.diary.home.presentation.RecentlyDeleted

@Composable
fun AvailableFoodsSurface(
    modifier: Modifier = Modifier,
    availableFoods: List<AvailableFood>,
    color: Color,
    onAction: (HomeActions) -> Unit,
    recentlyDeleted: RecentlyDeleted.AvailableFoodUndo? = null
) {
    Surface(
        modifier = modifier, color = color, shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Доступная еда",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd), onClick = {
                        onAction(HomeActions.ShowAvailableFoodDialog())
                    }) {
                    Icon(Icons.Default.Add, null)
                }
            }
            if (recentlyDeleted == null) {
                availableFoods.forEach { availableFood ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        onClick = {
                            onAction(HomeActions.ShowAvailableFoodDialog(availableFood))
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(availableFood.name)
                            IconButton(
                                onClick = {
                                    onAction(HomeActions.DeleteAvailableFood(availableFood))
                                }) {
                                Icon(Icons.Default.Delete, null)
                            }
                        }
                    }
                }
            } else {
                availableFoods.forEach { availableFood ->
                    if (recentlyDeleted.availableFood != availableFood) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                            onClick = {
                                onAction(HomeActions.ShowAvailableFoodDialog(availableFood))
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(availableFood.name)
                                IconButton(
                                    onClick = {
                                        onAction(HomeActions.DeleteAvailableFood(availableFood))
                                    }) {
                                    Icon(Icons.Default.Delete, null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewDynamicColors
@Composable
private fun AvailableFoodsSurfacePreview() {
    val availableFoods = (1..5).map {
        AvailableFood(
            id = it.toLong(), name = "Печенья"
        )
    }

    DiaryTheme(darkTheme = true) {
        AvailableFoodsSurface(
            availableFoods = availableFoods,
            color = MaterialTheme.colorScheme.surfaceVariant,
            onAction = {}
        )
    }
}