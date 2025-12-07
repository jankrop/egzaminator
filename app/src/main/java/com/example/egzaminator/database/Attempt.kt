package com.example.egzaminator.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attempt(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val createdAt: String,
    val correctAnswers: Int
)