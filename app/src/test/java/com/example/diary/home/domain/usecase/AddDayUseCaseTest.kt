package com.example.diary.home.domain.usecase

import com.example.diary.core.domain.model.Day
import com.example.diary.core.domain.repository.Repository
import com.google.common.truth.Truth.assertThat
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import java.time.LocalDate
import org.junit.Test

class AddDayUseCaseTest {
    private val repository = mockk<Repository>()
    private val addDayUseCase = AddDayUseCase(repository)

    @Test
    fun `invoke should not insert day when day already exists`() = runTest {
        val day = Day(
            id = 1,
            date = LocalDate.of(2025, 10, 10),
            foods = emptyList(),
            toilets = emptyList()
        )

        coEvery { repository.isDayExists(day) } returns true

        val result = addDayUseCase(day)
        assertThat(result).isEqualTo(null)

        coVerify(exactly = 1) { repository.isDayExists(day) }
        coVerify(exactly = 0) { repository.insertDay(day) }
    }

    @Test
    fun `invoke should insert day when day does not exists`() = runTest {
        val day = Day(
            id = 1,
            date = LocalDate.of(2025, 10, 10),
            foods = emptyList(),
            toilets = emptyList()
        )

        coEvery { repository.isDayExists(day) } returns false
        coEvery { repository.insertDay(day) } returns 1

        val result = addDayUseCase(day)
        assertThat(result).isEqualTo(1)

        coVerify(exactly = 1) { repository.isDayExists(day) }
        coVerify(exactly = 1) { repository.insertDay(day) }
        coVerify(ordering = Ordering.ORDERED) {
            repository.isDayExists(day)
            repository.insertDay(day)
        }
    }
}