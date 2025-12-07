package com.example.egzaminator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CorrectQuestion(
    @PrimaryKey val questionId: Int,
)