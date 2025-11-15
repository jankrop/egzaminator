package com.example.kotlin_wstep

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attempt(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val correctAnswers: Int
)