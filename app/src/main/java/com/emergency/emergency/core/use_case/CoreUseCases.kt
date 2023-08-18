package com.emergency.emergency.core.use_case

data class CoreUseCases(
    val localDateFromString: LocalDateFromString,
    val localDateToString: LocalDateToString,
    val reformatDate: ReformatDate,
    val trimWord: TrimWord,
    val getPreviousDates: GetPreviousDates,
    val validatePhoneNumber: ValidatePhoneNumber,
    val formatPhoneNumber: FormatPhoneNumber
)
