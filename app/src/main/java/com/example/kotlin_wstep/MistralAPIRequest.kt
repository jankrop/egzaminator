package com.example.kotlin_wstep

data class MistralAPIRequest(
    val max_tokens: Int = 256,
    val messages: Array<MistralAPIMessage>,
    val model: String = "mistral-medium-latest"
)
