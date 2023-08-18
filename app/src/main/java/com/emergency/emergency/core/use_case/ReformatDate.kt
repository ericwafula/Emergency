package com.emergency.emergency.core.use_case

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ReformatDate {
    operator fun invoke(date: String): Result {
        val frontendDtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val backendDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return try {
            val localDate = LocalDate.parse(date, frontendDtf)
            Result.Success(backendDtf.format(localDate))
        } catch (e: DateTimeParseException) {
            return Result.Error("invalid date")
        }
    }

    sealed class Result {
        data class Success(var date: String) : Result()
        data class Error(var message: String) : Result()
    }
}