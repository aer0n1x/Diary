package com.example.diary.home.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diary.core.domain.error.onError
import com.example.diary.core.domain.error.onSuccess
import com.example.diary.core.domain.model.Day
import com.example.diary.core.domain.repository.Repository
import com.example.diary.core.presentation.navigation.Destination
import com.example.diary.core.presentation.navigation.Navigator
import com.example.diary.home.domain.usecase.AddAvailableFoodUseCase
import com.example.diary.home.domain.usecase.AddDayUseCase
import com.example.diary.home.domain.usecase.EditAvailableFoodUseCase
import com.example.diary.home.domain.usecase.validate.AvailableFoodValidateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel(
    private val repository: Repository,
    private val navigator: Navigator,
    private val availableFoodValidateUseCase: AvailableFoodValidateUseCase,
    private val addAvailableFoodUseCase: AddAvailableFoodUseCase,
    private val editAvailableFoodUseCase: EditAvailableFoodUseCase,
    private val addDayUseCase: AddDayUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.onStart {
        observeDays()
        observeAvailableFoods()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    val snackbarHostState = SnackbarHostState()

    private fun observeDays() {
        repository.getDays().onEach { days ->
            _state.update {
                it.copy(days = days)
            }
        }.launchIn(viewModelScope)
    }

    private fun observeAvailableFoods() {
        repository.getAvailableFoods().onEach { availableFoods ->
            _state.update {
                it.copy(availableFoods = availableFoods)
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: HomeActions) {
        viewModelScope.launch {
            when (action) {
                is HomeActions.AddAvailableFood -> {
                    addAvailableFoodUseCase(action.inputText)
                }

                is HomeActions.DeleteAvailableFood -> {
                    snackbarHostState.currentSnackbarData?.dismiss()

                    _state.update {
                        it.copy(
                            recentlyDeleted = RecentlyDeleted.AvailableFoodUndo(action.availableFood)
                        )
                    }

                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = "Еда была удалена",
                        actionLabel = "Отмена",
                        duration = SnackbarDuration.Short
                    )

                    when (snackbarResult) {
                        SnackbarResult.Dismissed -> {
                            repository.deleteAvailableFoods(listOf(action.availableFood))
                        }

                        SnackbarResult.ActionPerformed -> {
                            _state.update {
                                it.copy(recentlyDeleted = null)
                            }
                        }
                    }
                }

                is HomeActions.DeleteDay -> {
                    snackbarHostState.currentSnackbarData?.dismiss()

                    _state.update {
                        it.copy(
                            recentlyDeleted = RecentlyDeleted.DayUndo(action.day)
                        )
                    }

                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = "День удален",
                        actionLabel = "Отмена",
                        duration = SnackbarDuration.Short
                    )

                    when (snackbarResult) {
                        SnackbarResult.Dismissed -> {
                            repository.deleteDay(action.day)
                        }

                        SnackbarResult.ActionPerformed -> {
                            _state.update {
                                it.copy(recentlyDeleted = null)
                            }
                        }
                    }
                }

                HomeActions.CloseAvailableFoodDialog -> {
                    _state.update {
                        it.copy(
                            availableFoodDialogState = false,
                            availableFood = null
                        )
                    }
                }

                is HomeActions.ShowAvailableFoodDialog -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    _state.update {
                        it.copy(
                            availableFoodDialogState = true,
                            availableFood = action.availableFood
                        )
                    }
                }

                is HomeActions.ValidateAddAvailableFoodDialog -> {
                    availableFoodValidateUseCase(action.inputText)
                        .onSuccess {
                            _state.update {
                                it.copy(
                                    availableFoodDialogValidationState = true
                                )
                            }
                        }
                        .onError {
                            _state.update {
                                it.copy(
                                    availableFoodDialogValidationState = false
                                )
                            }
                        }
                }

                is HomeActions.onFabButtonClick -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val dayId = addDayUseCase(
                        Day(
                            date = LocalDate.now()
                        )
                    )
                    if (dayId != null) {
                        navigateToEditDay(dayId)
                    } else {
                        state.value.days.firstOrNull()?.let {
                            navigateToEditDay(it.id)
                        }
                    }
                }

                is HomeActions.EditAvailableFood -> {
                    editAvailableFoodUseCase(action.availableFood)
                }
            }
        }
    }

    fun navigateToEditDay(dayId: Long) {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.EditDayScreen(dayId)
            )
        }
    }
}