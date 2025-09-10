package com.example.diary.home.domain.usecase.validate

import com.example.diary.core.domain.error.EmptyResult
import com.example.diary.core.domain.error.Result
import com.example.diary.core.domain.error.onError
import com.example.diary.core.domain.error.onSuccess
import com.example.diary.core.domain.repository.Repository
import com.example.diary.home.domain.usecase.format.TextFormatter
import kotlinx.coroutines.flow.first

class AvailableFoodValidateUseCase(
    private val textFormatter: TextFormatter,
    private val repository: Repository
) : AvailableFoodValidator {
    override suspend operator fun invoke(inputText: String): EmptyResult<AvailableFoodValidatorResult> {
        val existingAvailableFoods = repository.getAvailableFoods().first()
        textFormatter(inputText)
            .onError {
                return Result.Error(AvailableFoodValidatorResult.TextCantBeFormatted)
            }
            .onSuccess { formattedText ->
                val isAvailableFoodExists = existingAvailableFoods.any {
                    it.name.equals(formattedText, true)
                }

                return if (isAvailableFoodExists) {
                    Result.Error(AvailableFoodValidatorResult.AvailableFoodAlreadyExists)
                } else {
                    Result.Success(Unit)
                }
            }
        return Result.Error(AvailableFoodValidatorResult.Unknown)
    }
}