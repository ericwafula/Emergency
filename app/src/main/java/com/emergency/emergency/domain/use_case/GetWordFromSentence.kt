package com.emergency.emergency.domain.use_case

class GetWordFromSentence {
    operator fun invoke(sentence: String, index: Int): String {
        return sentence.split(" ")[index].trim()
    }
}