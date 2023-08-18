package com.emergency.emergency.core.use_case

import java.time.LocalDate

class GetPreviousDates {
    operator fun invoke(): List<LocalDate> {
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        val beginningOfTime = today.minusYears(10)

        val dates = mutableListOf<LocalDate>()
        var currentDate = yesterday

        while (currentDate.isAfter(beginningOfTime)) {
            dates.add(currentDate)
            currentDate = currentDate.minusDays(1)
        }
        return dates
    }
}