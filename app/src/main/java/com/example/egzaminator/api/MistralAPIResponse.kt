package com.example.egzaminator.api

data class ChatCompletionChoice(
    val message: MistralAPIMessage
)

data class MistralAPIResponse(
    val choices: Array<ChatCompletionChoice>
)