package com.example.diary.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.home.presentation.HomeActions

@Composable
fun AvailableFoodDialog(
    isEnabled: Boolean, isButtonEnabled: Boolean, onAction: (HomeActions) -> Unit, availableFood: AvailableFood? = null
) {
    var inputText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(isEnabled) {
        inputText = availableFood?.name ?: ""
        onAction(HomeActions.ValidateAddAvailableFoodDialog(inputText))
    }

    if (isEnabled) {
        Dialog(
            onDismissRequest = {
                onAction(HomeActions.CloseAvailableFoodDialog)
                inputText = ""
            }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = AlertDialogDefaults.containerColor,
                shape = AlertDialogDefaults.shape,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = if (availableFood == null) "Добавить еду" else "Редактировать еду",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AlertDialogDefaults.titleContentColor
                    )
                    OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = inputText, onValueChange = {
                        onAction(HomeActions.ValidateAddAvailableFoodDialog(it))
                        inputText = it
                    }, singleLine = true, label = {
                        Text("Еда")
                    }, placeholder = {
                        Text("Введите название еды")
                    })
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                onAction(HomeActions.CloseAvailableFoodDialog)
                                inputText = ""
                            }
                        ) {
                            Text(text = "Отмена")
                        }
                        TextButton(
                            enabled = isButtonEnabled, onClick = {
                                if (availableFood == null) {
                                    onAction(HomeActions.AddAvailableFood(inputText))
                                } else {
                                    onAction(HomeActions.EditAvailableFood(
                                        availableFood.copy(
                                            name = inputText
                                        )
                                    ))
                                }
                                onAction(HomeActions.CloseAvailableFoodDialog)
                                inputText = ""
                            }
                        ) {
                            Text(text = if (availableFood == null) "Добавить" else "Сохранить")
                        }
                    }
                }
            }
        }
    }
}