package com.example.kotlin_wstep

data class ChatCompletionChoice(
    val message: MistralAPIMessage
)

data class MistralAPIResponse(
    val choices: Array<ChatCompletionChoice>
)