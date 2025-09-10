package com.example.diary.home.domain.usecase

import com.example.diary.core.domain.error.Result
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.repository.Repository
import com.example.diary.home.domain.usecase.format.TextFormatter
import com.example.diary.home.domain.usecase.format.TextFormatterResult
import com.example.diary.home.domain.usecase.validate.AvailableFoodValidator
import com.example.diary.home.domain.usecase.validate.AvailableFoodValidatorResult
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EditAvailableFoodUseCaseTest {
    private val repository = mockk<Repository>()
    private val availableFoodValidator = mockk<AvailableFoodValidator>()
    private val textFormatter = mockk<TextFormatter>()
    private val editAvailableFoodUseCase = EditAvailableFoodUseCase(repository, availableFoodValidator, textFormatter)

    @Test
    fun `invoke should not insert available food when TextFormatter returns error`() = runTest {
        val formattedFood = "Food"
        val availableFood = AvailableFood(
            id = 2,
            name = "FOOd"
        )

        coEvery { textFormatter(availableFood.name) } returns
                Result.Error(TextFormatterResult.FirstCharIsNotLetter)
        coEvery { repository.upsertAvailableFoods(any()) } returns Unit

        editAvailableFoodUseCase(availableFood)

        coVerify(exactly = 1) { textFormatter(availableFood.name) }
        coVerify(exactly = 0) {
            repository.upsertAvailableFoods(
                listOf(
                    availableFood.copy(
                        name = formattedFood
                    )
                )
            )
        }
    }

    @Test
    fun `invoke should not insert available food when AvailableFoodValidator returns error`() = runTest {
        val formattedFood = "Food"
        val availableFood = AvailableFood(
            id = 2,
            name = "FOOd"
        )

        coEvery { textFormatter(availableFood.name) } returns Result.Success(formattedFood)
        coEvery { availableFoodValidator(formattedFood) } returns
                Result.Error(AvailableFoodValidatorResult.AvailableFoodAlreadyExists)
        coEvery { repository.upsertAvailableFoods(any()) } returns Unit

        editAvailableFoodUseCase(availableFood)

        coVerify(exactly = 1) { textFormatter(availableFood.name) }
        coVerify(exactly = 1) { availableFoodValidator(formattedFood) }
        coVerify(ordering = Ordering.ORDERED) {
            textFormatter(availableFood.name)
            availableFoodValidator(formattedFood)
        }
        coVerify(exactly = 0) {
            repository.upsertAvailableFoods(
                listOf(
                    availableFood.copy(
                        name = formattedFood
                    )
                )
            )
        }
    }

    @Test
    fun `invoke should insert available food when all valid`() = runTest {
        val formattedFood = "Food"
        val availableFood = AvailableFood(
            id = 2,
            name = "FOOd"
        )

        coEvery { textFormatter(availableFood.name) } returns Result.Success(formattedFood)
        coEvery { availableFoodValidator(formattedFood) } returns Result.Success(Unit)
        coEvery { repository.upsertAvailableFoods(any()) } returns Unit

        editAvailableFoodUseCase(availableFood)

        coVerify(exactly = 1) { textFormatter(availableFood.name) }
        coVerify(exactly = 1) { availableFoodValidator(formattedFood) }
        coVerify(exactly = 1) {
            repository.upsertAvailableFoods(
                listOf(
                    availableFood.copy(
                        name = formattedFood
                    )
                )
            )
        }
        coVerify(ordering = Ordering.ORDERED) {
            textFormatter(availableFood.name)
            availableFoodValidator(formattedFood)
            repository.upsertAvailableFoods(
                listOf(
                    availableFood.copy(
                        name = formattedFood
                    )
                )
            )
        }
    }
}