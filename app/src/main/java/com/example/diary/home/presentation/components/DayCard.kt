package com.example.diary.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.dp
import com.example.diary.core.presentation.theme.DiaryTheme
import com.example.diary.core.domain.model.Day
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    day: Day,
    onDeleteClick: (Day) -> Unit,
    onNavigate: (Long) -> Unit
) {
    @Suppress("DEPRECATION")
    val date = day.date.format(DateTimeFormatter.ofPattern("dd MMM", Locale("ru")))

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        onClick = {
            onNavigate(day.id)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(date)
            IconButton(
                onClick = {
                    onDeleteClick(day)
                }
            ) {
                Icon(Icons.Default.Delete, null)
            }
        }
    }
}

@PreviewDynamicColors
@Composable
private fun DayCardPreview() {
    val day = Day(
        date = LocalDate.now(),
        foods = emptyList(),
        toilets = emptyList()
    )

    DiaryTheme(darkTheme = true) {
        DayCard(
            day = day,
            onDeleteClick = {},
            onNavigate = {}
        )
    }
}