package com.example.diary.home.domain.usecase.format

import com.example.diary.core.domain.error.Result

class FormatTextUseCase : TextFormatter {
    override operator fun invoke(inputText: String): Result<String, TextFormatterResult> {
        if (inputText.isBlank()) return Result.Error(TextFormatterResult.IsBlank)

        val trimmed = inputText.trim()
        if (!trimmed.first().isLetter()) return Result.Error(TextFormatterResult.FirstCharIsNotLetter)

        val formated = trimmed.lowercase().replaceFirstChar { it.uppercase() }
        return Result.Success(formated)
    }
}