package com.example.diary.home.domain.usecase.validate

import com.example.diary.core.domain.error.Result
import com.example.diary.core.domain.model.AvailableFood
import com.example.diary.core.domain.repository.Repository
import com.example.diary.home.domain.usecase.format.TextFormatter
import com.example.diary.home.domain.usecase.format.TextFormatterResult
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class AvailableFoodValidateUseCaseTest {
    private val textFormatter = mockk<TextFormatter>()
    private val repository = mockk<Repository>()
    private val availableFoodValidateUseCase = AvailableFoodValidateUseCase(textFormatter, repository)
    private val availableFoods = (1..10).map {
        AvailableFood(
            id = it.toLong(),
            name = "Food $it"
        )
    }

    @Test
    fun `invoke should return TextIsNotFormatted error when TextFormatter returns error`() = runTest {
        val inputText = "1hello"

        coEvery { textFormatter(inputText) } returns Result.Error(TextFormatterResult.FirstCharIsNotLetter)
        coEvery { repository.getAvailableFoods() } returns flowOf(availableFoods)

        val result = availableFoodValidateUseCase(inputText)
        assertTrue(result is Result.Error)

        val errorResult = result as Result.Error
        assertEquals(AvailableFoodValidatorResult.TextCantBeFormatted, errorResult.error)

        coVerify(exactly = 1) { repository.getAvailableFoods() }
        coVerify(exactly = 1) { textFormatter(inputText) }
    }

    @Test
    fun `invoke should return AvailableFoodAlreadyExists error when AvailableFood already exists`() = runTest {
        val inputText = "Food 5"

        coEvery { textFormatter(inputText) } returns Result.Success(inputText)
        coEvery { repository.getAvailableFoods() } returns flowOf(availableFoods)

        val result = availableFoodValidateUseCase(inputText)
        assertTrue(result is Result.Error)

        val errorResult = result as Result.Error
        assertEquals(AvailableFoodValidatorResult.AvailableFoodAlreadyExists, errorResult.error)

        coVerify(exactly = 1) {
            repository.getAvailableFoods()
            textFormatter(inputText)
        }
    }

    @Test
    fun `invoke should return success result if all valid`() = runTest {
        val inputText = "Food"

        coEvery { textFormatter(inputText) } returns Result.Success(inputText)
        coEvery { repository.getAvailableFoods() } returns flowOf(availableFoods)

        val result = availableFoodValidateUseCase(inputText)
        assertTrue(result is Result.Success)

        val successResult = result as Result.Success
        assertEquals(Unit, successResult.data)

        coVerify(exactly = 1) { repository.getAvailableFoods() }
        coVerify(exactly = 1) { textFormatter(inputText) }
    }

    @Test
    fun `invoke should handle case-insensitive comparison`() = runTest {
        val inputText = "fOOd 5"

        coEvery { textFormatter(inputText) } returns Result.Success(inputText)
        coEvery { repository.getAvailableFoods() } returns flowOf(availableFoods)

        val result = availableFoodValidateUseCase(inputText)
        assertTrue(result is Result.Error)

        val errorResult = result as Result.Error
        assertEquals(AvailableFoodValidatorResult.AvailableFoodAlreadyExists, errorResult.error)

        coVerify(exactly = 1) { repository.getAvailableFoods() }
        coVerify(exactly = 1) { textFormatter(inputText) }
        coVerify(ordering = Ordering.ORDERED) {
            repository.getAvailableFoods()
            textFormatter(inputText)
        }
    }
}