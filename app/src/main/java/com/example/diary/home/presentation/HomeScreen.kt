package com.example.diary.home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import com.example.diary.R
import com.example.diary.core.presentation.theme.DiaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.app_name))
            })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {

            }) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }) { innerPadding ->

    }
}

@PreviewDynamicColors
@Composable
fun HomeScreenPreview() {
    DiaryTheme(darkTheme = true) {
        HomeScreen()
    }
}