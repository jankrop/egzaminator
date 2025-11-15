package com.example.kotlin_wstep

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AttemptDao {
    @Query("SELECT * FROM attempt")
    fun loadAllAttempts(): Array<Attempt>

    @Insert
    fun insertAttempt(attempt: Attempt)
}