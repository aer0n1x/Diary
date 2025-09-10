package com.example.diary.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.diary.core.domain.model.Day
import com.example.diary.home.presentation.HomeActions
import com.example.diary.home.presentation.RecentlyDeleted
import java.time.LocalDate

@Composable
fun DaysSurface(
    modifier: Modifier = Modifier,
    days: List<Day>,
    color: Color,
    onAction: (HomeActions) -> Unit,
    onNavigate: (Long) -> Unit,
    recentlyDeleted: RecentlyDeleted.DayUndo? = null
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
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Дни", style = MaterialTheme.typography.titleLarge
                )
            }
            if (recentlyDeleted == null) {
                days.forEach { day ->
                    DayCard(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        day = day,
                        onDeleteClick = {
                            onAction(HomeActions.DeleteDay(day))
                        },
                        onNavigate = onNavigate
                    )
                }
            } else {
                days.forEach { day ->
                    if (recentlyDeleted.day != day) {
                        DayCard(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            day = day,
                            onDeleteClick = {
                                onAction(HomeActions.DeleteDay(day))
                            },
                            onNavigate = onNavigate
                        )
                    }
                }
            }
        }
    }
}

@PreviewDynamicColors
@Composable
private fun DaysSurfacePreview() {
    val days = (1..5).map {
        Day(
            id = it.toLong(),
            date = LocalDate.now(),
            foods = emptyList(),
            toilets = emptyList()
        )
    }

    DiaryTheme(darkTheme = true) {
        DaysSurface(
            days = days,
            color = MaterialTheme.colorScheme.surfaceVariant,
            onAction = {},
            onNavigate = {}
        )
    }
}