package com.example.egzaminator

data class ChatCompletionChoice(
    val message: MistralAPIMessage
)

data class MistralAPIResponse(
    val choices: Array<ChatCompletionChoice>
)