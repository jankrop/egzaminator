package com.example.kotlin_wstep

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CorrectQuestionDao {
    @Query("SELECT * FROM correctquestion")
    fun getQuestions(): List<CorrectQuestion>

    @Insert
    fun insertQuestion(correctQuestion: CorrectQuestion)
}