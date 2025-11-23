package com.example.kotlin_wstep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.Console

class TestActivity : AppCompatActivity(R.layout.activity_test), OnNextQuestionListener {
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var test: List<Question> = emptyList()
    private var db: AppDatabase? = null

    fun createFragment() {
        val questionId = correctAnswers + wrongAnswers

        val correctAnswerPercentage = if (questionId == 0) 0 else correctAnswers * 100 / questionId

        val toolbar = findViewById<Toolbar>(R.id.scoreToolbar)
        toolbar.title = if (questionId < 40) "Pytanie ${questionId+1}/40" else "Koniec testu"
        toolbar.subtitle = "${correctAnswers} poprawnych, ${wrongAnswers} błędnych " +
                "(${correctAnswerPercentage}%)"

        val fragment = if (questionId < 3) (
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt("questionId", test[questionId].questionId)
                    putString("question", test[questionId].question)
                    putStringArray("answers", test[questionId].answers)
                    putInt("correctAnswer", test[questionId].correctAnswer)
                    putInt("imageId", test[questionId].imageId)
                }
            }
        ) else (
            FinishFragment().apply {
                arguments = Bundle().apply {
                    putInt("correct", correctAnswers)
                }
            }
        )

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

        db = DatabaseProvider.getDatabase(applicationContext)

        val correctIDs = db?.correctQuestionDao()?.getQuestions()?.map { it.questionId }
        val questions = CsvUtils.readCSVFromRaw(this, R.raw.questions)
        val unansweredQuestions = questions.filter { correctIDs?.contains(it.questionId) != true  }
        val answeredQuestions = questions.filter { correctIDs?.contains(it.questionId) == true  }

        test = if (unansweredQuestions.size >= 40) {
            unansweredQuestions.shuffled().take(40)
        } else {
            unansweredQuestions + answeredQuestions.shuffled().take(40 - unansweredQuestions.size)
        }

        createFragment()
    }

    override fun onNextQuestion(id: Int?, isCorrect: Boolean) {
        if (id == null || db == null) return

        if (isCorrect) {
            db?.correctQuestionDao()?.insertQuestion(CorrectQuestion(questionId = id))
            correctAnswers++
        }
        else wrongAnswers++

        createFragment()
    }
}
