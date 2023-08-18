package com.emergency.emergency.core.use_case

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateToString {
    operator fun invoke(localDate: LocalDate, pattern: String = "dd/MM/yyyy"): String {
        val dtf = DateTimeFormatter.ofPattern(pattern)
        return localDate.format(dtf)
    }
}