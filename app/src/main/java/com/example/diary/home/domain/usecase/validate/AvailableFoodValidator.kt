package com.example.diary.home.domain.usecase.validate

import com.example.diary.core.domain.error.EmptyResult
import com.example.diary.core.domain.error.Error

interface AvailableFoodValidator {
    suspend operator fun invoke(inputText: String) : EmptyResult<AvailableFoodValidatorResult>
}

sealed interface AvailableFoodValidatorResult : Error {
    object TextCantBeFormatted : AvailableFoodValidatorResult
    object AvailableFoodAlreadyExists : AvailableFoodValidatorResult
    object Unknown : AvailableFoodValidatorResult
}