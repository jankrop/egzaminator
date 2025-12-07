package com.example.egzaminator.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CorrectQuestion(
    @PrimaryKey val questionId: Int,
)