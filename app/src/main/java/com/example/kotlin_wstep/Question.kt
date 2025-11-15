package com.example.kotlin_wstep


data class Question(
    val questionId: Int,
    val question: String,
    val answers: Array<String>,
    val correctAnswer: Int,
    val imageId: Int,
)