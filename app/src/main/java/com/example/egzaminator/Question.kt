package com.example.egzaminator


data class Question(
    val questionId: Int,
    val question: String,
    val answers: Array<String>,
    val correctAnswer: Int,
    val imageId: Int,
)