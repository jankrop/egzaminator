package com.example.kotlin_wstep

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CorrectQuestion(
    @PrimaryKey val questionId: Int,
)