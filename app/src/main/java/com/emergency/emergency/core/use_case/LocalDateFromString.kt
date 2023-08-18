package com.emergency.emergency.core.use_case

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateFromString {
    operator fun invoke(date: String): Result {
        if (date.isBlank()) return Result.Error("invalid date format")
        val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val localDate = try {
            LocalDate.parse(date, dtf)
        } catch (e: DateTimeParseException) {
            return Result.Error("invalid date format")
        }
        return Result.Success(localDate)
    }

    sealed class Result {
        data class Success(var localDate: LocalDate) : Result()
        data class Error(val message: String) : Result()
    }
}