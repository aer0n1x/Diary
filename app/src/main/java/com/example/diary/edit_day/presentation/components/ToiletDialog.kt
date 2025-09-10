@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.diary.edit_day.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.diary.core.domain.model.Toilet
import com.example.diary.core.domain.model.ToiletLevel
import com.example.diary.edit_day.presentation.EditDayActions
import java.time.LocalTime

@Composable
fun ToiletDialog(
    isEnabled: Boolean,
    dayId: Long,
    toilet: Toilet? = null,
    onAction: (EditDayActions) -> Unit,
) {
    var selectedItem by remember {
        mutableStateOf<ToiletLevel?>(null)
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var textFieldText by remember {
        mutableStateOf("")
    }
    val isButtonEnabled by remember {
        derivedStateOf { selectedItem != null }
    }
    val timePickerState = remember(toilet) {
        val localTime = toilet?.time ?: LocalTime.now()
        TimePickerState(localTime.hour, localTime.minute, is24Hour = true)
    }

    LaunchedEffect(toilet) {
        selectedItem = toilet?.toiletLevel
        textFieldText = selectedItem?.name ?: ""
    }

    if (isEnabled) {
        Dialog(
            onDismissRequest = {
                onAction(EditDayActions.CloseAddToiletDialog)
                selectedItem = null
                textFieldText = ""
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
                        text = if (toilet == null) "Добавить туалет" else "Редактировать туалет",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AlertDialogDefaults.titleContentColor
                    )
                    TimeInput(state = timePickerState)
                    ExposedDropdownMenuBox(
                        expanded = isExpanded, onExpandedChange = {
                            isExpanded = it
                        }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                            value = textFieldText,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(isExpanded)
                            },
                            label = {
                                Text("Поиск")
                            },
                            placeholder = {
                                Text("Выберете уровень туалета")
                            },
                            readOnly = true,
                            onValueChange = {}
                        )

                        ExposedDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = {
                                isExpanded = false
                            },
                            modifier = Modifier.heightIn(min = 0.dp, max = 150.dp)
                        ) {
                            ToiletLevel.entries.forEach { toiletLevel ->
                                DropdownMenuItem(text = {
                                    Text(toiletLevel.text)
                                }, onClick = {
                                    selectedItem = toiletLevel
                                    textFieldText = toiletLevel.text
                                    isExpanded = false
                                })
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                onAction(EditDayActions.CloseAddToiletDialog)
                            }) {
                            Text(text = "Отмена")
                        }
                        TextButton(
                            enabled = isButtonEnabled,
                            onClick = {
                                selectedItem?.let { selectedItem ->
                                    if (toilet == null) {
                                        onAction(EditDayActions.AddToilet(
                                            Toilet(
                                                toiletLevel = selectedItem,
                                                time = LocalTime.of(timePickerState.hour, timePickerState.minute),
                                                dayId = dayId
                                            )
                                        ))
                                    } else {
                                        onAction(EditDayActions.EditToilet(
                                            Toilet(
                                                id = toilet.id,
                                                toiletLevel = selectedItem,
                                                time = LocalTime.of(
                                                    timePickerState.hour, timePickerState.minute
                                                ),
                                                dayId = dayId
                                            )
                                        ))
                                    }
                                }

                                onAction(EditDayActions.CloseAddToiletDialog)
                                selectedItem = null
                                textFieldText = ""
                            }) {
                            Text(text = if (toilet == null) "Добавить" else "Сохранить")
                        }
                    }
                }
            }
        }
    }
}