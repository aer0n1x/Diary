package com.example.diary.home.domain.usecase.format

import com.example.diary.core.domain.error.Result
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FormatTextUseCaseTest {
    private val formatTextUseCase = FormatTextUseCase()

    @Test
    fun `Invoke with blank inputText should return IsBlank error`() {
        val inputText = "    "

        val result = formatTextUseCase(inputText)
        assertThat(result).isInstanceOf(Result.Error::class.java)

        val errorResult = result as Result.Error
        assertThat(errorResult.error).isEqualTo(TextFormatterResult.IsBlank)
    }

    @Test
    fun `Invoke with non letter first char should return FirstCharIsNotLetter error`() {
        val inputTextWithFirstSymbol = ".Hello"
        val inputTextWithFirstDigit = "1Hello"

        val resultSymbol = formatTextUseCase(inputTextWithFirstSymbol)
        val resultDigit = formatTextUseCase(inputTextWithFirstDigit)
        assertThat(resultSymbol).isInstanceOf(Result.Error::class.java)
        assertThat(resultDigit).isInstanceOf(Result.Error::class.java)

        val errorResultSymbol = resultSymbol as Result.Error
        val errorResultDigit = resultDigit as Result.Error
        assertThat(errorResultSymbol.error).isEqualTo(TextFormatterResult.FirstCharIsNotLetter)
        assertThat(errorResultDigit.error).isEqualTo(TextFormatterResult.FirstCharIsNotLetter)
    }

    @Test
    fun `Invoke with empty text should return IsBlank error`() {
        val inputText = ""

        val result = formatTextUseCase(inputText)
        assertThat(result).isInstanceOf(Result.Error::class.java)

        val errorResult = result as Result.Error
        assertThat(errorResult.error).isEqualTo(TextFormatterResult.IsBlank)
    }

    @Test
    fun `Invoke with valid text should return formatted text`() {
        val inputText = "   hEllO   "

        val result = formatTextUseCase(inputText)
        assertThat(result).isInstanceOf(Result.Success::class.java)

        val successResult = result as Result.Success
        assertThat(successResult.data).isEqualTo("Hello")
    }
}