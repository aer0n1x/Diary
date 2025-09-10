package com.example.diary.edit_day.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diary.core.domain.model.Food
import com.example.diary.core.domain.model.Toilet
import com.example.diary.core.domain.repository.Repository
import com.example.diary.core.presentation.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditDayViewModel(
    private val repository: Repository,
    private val navigator: Navigator
) : ViewModel() {
    private val _state = MutableStateFlow(EditDayState())
    val state = _state.onStart {
        observeAvailableFoods()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun onStart(dayId: Long) {
        viewModelScope.launch {
            val day = repository.getDay(dayId)
            _state.update {
                it.copy(
                    foods = day.foods,
                    oldFoods = day.foods,
                    toilets = day.toilets,
                    oldToilets = day.toilets,
                    dayId = day.id
                )
            }
        }
    }

    fun onAction(action: EditDayActions) {
        viewModelScope.launch {
            when (action) {
                is EditDayActions.AddFood -> {
                    _state.update {
                        it.copy(
                            foods = it.foods + action.food
                        )
                    }
                }

                is EditDayActions.AddToilet -> {
                    _state.update {
                        it.copy(
                            toilets = it.toilets + action.toilet
                        )
                    }
                }

                EditDayActions.CloseAddFoodDialog -> {
                    _state.update {
                        it.copy(
                            foodDialogState = false, food = null
                        )
                    }
                }

                EditDayActions.CloseAddToiletDialog -> {
                    _state.update {
                        it.copy(
                            toiletDialogState = false, toilet = null
                        )
                    }
                }

                is EditDayActions.DeleteFood -> {
                    _state.update {
                        it.copy(
                            foods = it.foods - action.food
                        )
                    }
                }

                is EditDayActions.DeleteToilet -> {
                    _state.update {
                        it.copy(
                            toilets = it.toilets - action.toilet
                        )
                    }
                }

                is EditDayActions.ShowFoodDialog -> {
                    _state.update {
                        it.copy(
                            foodDialogState = true, food = action.food
                        )
                    }
                }

                is EditDayActions.ShowToiletDialog -> {
                    _state.update {
                        it.copy(
                            toiletDialogState = true, toilet = action.toilet
                        )
                    }
                }

                is EditDayActions.SaveDay -> {
                    val foods = state.value.foods
                    val oldFoods = state.value.oldFoods
                    val toilets = state.value.toilets
                    val oldToilets = state.value.oldToilets

                    viewModelScope.launch {
                        deleteFoods(oldFoods, foods)
                        deleteToilets(oldToilets, toilets)

                        if (foods != oldFoods) {
                            repository.upsertFoods(foods)
                        }
                        if (toilets != oldToilets) {
                            repository.upsertToilets(toilets)
                        }
                    }
                }

                is EditDayActions.EditFood -> {
                    _state.update {
                        it.copy(
                            foods = state.value.foods.map { food -> if (food.id == action.food.id) action.food else food }
                        )
                    }
                }

                is EditDayActions.EditToilet -> {
                    _state.update {
                        it.copy(
                            toilets = state.value.toilets.map { toilet -> if (toilet.id == action.toilet.id) action.toilet else toilet }
                        )
                    }
                }
            }
        }
    }

    fun navigateUp() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }

    private suspend fun deleteFoods(
        oldFoods: List<Food>, foods: List<Food>
    ) {
        val foodsToDelete = mutableListOf<Food>()

        oldFoods.forEach { oldFood ->
            var isFoodToDelete = true

            foods.forEach { food ->
                if (food.id == oldFood.id) {
                    isFoodToDelete = false
                }
            }

            if (isFoodToDelete) {
                foodsToDelete += oldFood
            }
        }

        if (foodsToDelete.isNotEmpty()) {
            repository.deleteFoods(foodsToDelete)
        }
    }

    private suspend fun deleteToilets(
        oldToilets: List<Toilet>, toilets: List<Toilet>
    ) {
        val toiletsToDelete = mutableListOf<Toilet>()

        oldToilets.forEach { oldToilet ->
            var isToiletToDelete = true

            toilets.forEach { toilet ->
                if (toilet.id == oldToilet.id) {
                    isToiletToDelete = false
                }
            }

            if (isToiletToDelete) {
                toiletsToDelete += oldToilet
            }
        }

        if (toiletsToDelete.isNotEmpty()) {
            repository.deleteToilets(toiletsToDelete)
        }
    }

    private fun observeAvailableFoods() {
        repository.getAvailableFoods().onEach { availableFoods ->
            _state.update {
                it.copy(
                    availableFoods = availableFoods
                )
            }
        }.launchIn(viewModelScope)
    }
}