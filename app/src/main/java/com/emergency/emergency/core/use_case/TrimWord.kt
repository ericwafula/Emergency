package com.emergency.emergency.core.use_case

class TrimWord {
    operator fun invoke(word: String, start: Int = 10, end: Int = 13): String {
        return if (word.isNotBlank()) {
            val newWord = buildString {
                word.forEachIndexed { index, c ->
                    if (index == end) return@buildString
                    if (index >= start) append(".") else append(c)
                }
            }
            newWord
        } else ""
    }
}