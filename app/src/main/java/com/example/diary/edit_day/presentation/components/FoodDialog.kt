@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.diary.edit_day.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.model.Food
import com.example.diary.edit_day.presentation.EditDayActions
import java.time.LocalTime

@Composable
fun FoodDialog(
    isEnabled: Boolean,
    availableFoods: List<AvailableFood>,
    dayId: Long,
    food: Food? = null,
    onAction: (EditDayActions) -> Unit,
) {
    var selectedItem by remember {
        mutableStateOf<AvailableFood?>(null)
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var inputText by remember {
        mutableStateOf("")
    }
    val isButtonEnabled by remember {
        derivedStateOf { selectedItem != null }
    }
    val searchedAvailableFoods = remember(inputText) {
        if (inputText.isBlank()) {
            emptyList()
        } else {
            availableFoods.filter { availableFood ->
                availableFood.name.contains(inputText, true)
            }
        }
    }
    val timePickerState = remember(food) {
        val localTime = food?.time ?: LocalTime.now()
        TimePickerState(localTime.hour, localTime.minute, is24Hour = true)
    }

    LaunchedEffect(food) {
        selectedItem = if (food == null) null else availableFoods.find { it.id == food.foodId }
        inputText = selectedItem?.name ?: ""
    }

    if (isEnabled) {
        Dialog(
            onDismissRequest = {
                onAction(EditDayActions.CloseAddFoodDialog)
                selectedItem = null
                inputText = ""
            }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = AlertDialogDefaults.shape,
                color = AlertDialogDefaults.containerColor,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = if (food == null) "Добавить еду" else "Редактировать еду",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AlertDialogDefaults.titleContentColor
                    )
                    TimeInput(state = timePickerState)
                    ExposedDropdownMenuBox(
                        expanded = isExpanded, onExpandedChange = {
                            isExpanded = it
                        }) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryEditable, true),
                            value = inputText,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(isExpanded)
                            },
                            placeholder = {
                                Text("Введите название еды")
                            },
                            label = {
                                Text("Поиск")
                            },
                            readOnly = false,
                            singleLine = true,
                            onValueChange = {
                                inputText = it
                                selectedItem = null
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (searchedAvailableFoods.isNotEmpty()) {
                                        val firstSearchedAvailableFood = searchedAvailableFoods.first()

                                        selectedItem = firstSearchedAvailableFood
                                        inputText = firstSearchedAvailableFood.name
                                    }
                                    isExpanded = false
                                })
                        )

                        ExposedDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = {
                                isExpanded = false
                            },
                            modifier = Modifier.heightIn(min = 0.dp, max = 150.dp)
                        ) {
                            searchedAvailableFoods.forEach { searchedAvailableFood ->
                                DropdownMenuItem(text = {
                                    Text(searchedAvailableFood.name)
                                }, onClick = {
                                    selectedItem = searchedAvailableFood
                                    inputText = searchedAvailableFood.name
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
                                onAction(EditDayActions.CloseAddFoodDialog)
                            }) {
                            Text(text = "Отмена")
                        }
                        TextButton(
                            enabled = isButtonEnabled, onClick = {
                                selectedItem?.let {
                                    val foodId = it.id
                                    if (food == null) {
                                        val food = Food(
                                            foodId = foodId,
                                            time = LocalTime.of(timePickerState.hour, timePickerState.minute),
                                            dayId = dayId
                                        )

                                        onAction(EditDayActions.AddFood(food))
                                    } else {
                                        val food = Food(
                                            id = food.id,
                                            foodId = foodId,
                                            time = LocalTime.of(timePickerState.hour, timePickerState.minute),
                                            dayId = dayId
                                        )

                                        onAction(EditDayActions.EditFood(food))
                                    }
                                }
                                onAction(EditDayActions.CloseAddFoodDialog)
                                selectedItem = null
                                inputText = ""
                            }) {
                            Text(text = if (food == null) "Добавить" else "Сохранить")
                        }
                    }
                }
            }
        }
    }
}