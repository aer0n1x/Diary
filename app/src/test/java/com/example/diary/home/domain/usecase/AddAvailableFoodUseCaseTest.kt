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

class AddAvailableFoodUseCaseTest {
    private val repository = mockk<Repository>()
    private val availableFoodValidator = mockk<AvailableFoodValidator>()
    private val textFormatter = mockk<TextFormatter>()
    private val addAvailableFoodUseCase = AddAvailableFoodUseCase(repository, availableFoodValidator, textFormatter)

    @Test
    fun `invoke should not insert available food when TextFormatter returns error`() = runTest {
        val availableFoodName = "!Food"
        val formattedAvailableFood = "Food"

        coEvery { textFormatter(availableFoodName) } returns
                Result.Error(TextFormatterResult.FirstCharIsNotLetter)
        coEvery { repository.upsertAvailableFoods(any()) } returns Unit

        addAvailableFoodUseCase(availableFoodName)

        coVerify(exactly = 1) { textFormatter(availableFoodName) }
        coVerify(exactly = 0) {
            repository.upsertAvailableFoods(
                listOf(
                    AvailableFood(
                        name = formattedAvailableFood
                    )
                )
            )
        }
    }

    @Test
    fun `invoke should not insert available food when AvailableFoodValidator returns error`() = runTest {
        val availableFoodName = "FOOd"
        val formattedAvailableFood = "Food"

        coEvery { textFormatter(availableFoodName) } returns Result.Success(formattedAvailableFood)
        coEvery { availableFoodValidator(formattedAvailableFood) } returns
                Result.Error(AvailableFoodValidatorResult.AvailableFoodAlreadyExists)
        coEvery { repository.upsertAvailableFoods(any()) } returns Unit

        addAvailableFoodUseCase(availableFoodName)

        coVerify(exactly = 1) { textFormatter(availableFoodName) }
        coVerify(exactly = 1) { availableFoodValidator(formattedAvailableFood) }
        coVerify(ordering = Ordering.ORDERED) {
            textFormatter(availableFoodName)
            availableFoodValidator(formattedAvailableFood)
        }
        coVerify(exactly = 0) {
            repository.upsertAvailableFoods(
                listOf(
                    AvailableFood(
                        name = formattedAvailableFood
                    )
                )
            )
        }
    }

    @Test
    fun `invoke should insert available food when all valid`() = runTest {
        val availableFoodName = "FOOd"
        val formattedAvailableFood = "Food"

        coEvery { textFormatter(availableFoodName) } returns Result.Success(formattedAvailableFood)
        coEvery { availableFoodValidator(formattedAvailableFood) } returns Result.Success(Unit)
        coEvery { repository.upsertAvailableFoods(any()) } returns Unit

        addAvailableFoodUseCase(availableFoodName)

        coVerify(exactly = 1) { textFormatter(availableFoodName) }
        coVerify(exactly = 1) { availableFoodValidator(formattedAvailableFood) }
        coVerify(exactly = 1) {
            repository.upsertAvailableFoods(
                listOf(
                    AvailableFood(
                        name = formattedAvailableFood
                    )
                )
            )
        }
        coVerify(ordering = Ordering.ORDERED) {
            textFormatter(availableFoodName)
            availableFoodValidator(formattedAvailableFood)
            repository.upsertAvailableFoods(
                listOf(
                    AvailableFood(
                        name = formattedAvailableFood
                    )
                )
            )
        }
    }
}