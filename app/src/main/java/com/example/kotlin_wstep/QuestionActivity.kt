package com.example.kotlin_wstep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class QuestionActivity : AppCompatActivity(R.layout.activity_question) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val questions = CsvUtils.readCSVFromRaw(this, R.raw.questions)
        val test = questions.shuffled().take(40)

        val fragment = QuestionFragment().apply {
            arguments = Bundle().apply {
                putString("question", test[0].question)
                putStringArray("answers", test[0].answers)
                putInt("correctAnswer", test[0].correctAnswer)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.questionContainer, fragment)
            .commit()
    }
}
