package com.example.kotlin_wstep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class QuestionActivity : AppCompatActivity(R.layout.activity_question), OnNextQuestionListener {
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var test: List<Question> = emptyList()

    fun createFragment() {
        val questionId = correctAnswers + wrongAnswers
        if (questionId < 0 || questionId >= 40) return

        val correctAnswerPercentage = if (questionId == 0) 0 else correctAnswers * 100 / questionId

        val toolbar = findViewById<Toolbar>(R.id.scoreToolbar)
        toolbar.title = "Pytanie ${questionId+1}/40"
        toolbar.subtitle = "${correctAnswers} poprawnych, ${wrongAnswers} błędnych " +
                "(${correctAnswerPercentage}%)"

        val fragment = QuestionFragment().apply {
            arguments = Bundle().apply {
                putString("question", test[questionId].question)
                putStringArray("answers", test[questionId].answers)
                putInt("correctAnswer", test[questionId].correctAnswer)
                putInt("imageId", test[questionId].imageId)
            }
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out,
            )
            .replace(R.id.questionContainer, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val questions = CsvUtils.readCSVFromRaw(this, R.raw.questions)
        test = questions.shuffled().take(40)

        createFragment()
    }

    override fun onNextQuestion(isCorrect: Boolean) {
        if (isCorrect) correctAnswers++
        else wrongAnswers++

        createFragment()
    }
}
