package com.example.diary.home.domain.usecase.format

import com.example.diary.core.domain.error.Error
import com.example.diary.core.domain.error.Result

interface TextFormatter {
    operator fun invoke(inputText: String): Result<String, TextFormatterResult>
}

sealed interface TextFormatterResult : Error {
    object IsBlank : TextFormatterResult
    object FirstCharIsNotLetter : TextFormatterResult
}