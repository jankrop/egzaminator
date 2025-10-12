package com.example.kotlin_wstep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class QuestionActivity : AppCompatActivity(R.layout.activity_question) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val fragment = QuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt("questionId", (0..2).random())
                    putInt("correctAnswer", 1)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.questionContainer, fragment)
                .commit()
        }
    }
}
