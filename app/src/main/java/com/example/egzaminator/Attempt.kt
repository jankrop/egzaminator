package com.example.egzaminator

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Attempt(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val createdAt: String,
    val correctAnswers: Int
)