package com.example.kotlin_wstep

data class Question(
    val question: String,
    val answers: Array<String>,
    val correctAnswer: Int,
    val imageURL: String,
)