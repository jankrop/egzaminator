package com.example.kotlin_wstep

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.Fragment

class QuestionFragment : Fragment(R.layout.fragment_question) {
    private var questionId: Int? = null
    private var correctAnswer: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            questionId = it.getInt("questionId")
            correctAnswer = it.getInt("correctAnswer")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questionView = view.findViewById<TextView>(R.id.questionView)
        questionView.text = when (questionId) {
            0 -> "Jak długą trzeba mieć brodę żeby zostać sysadminem?"
            1 -> "Jak napisać pętlę for w HTMLu?"
            2 -> "Dlaczego JavaScript nas zachwyca?"
            else -> "Nie znaleziono pytania"
        }

        val checkButtonContainer = view.findViewById<FrameLayout>(R.id.checkButtonContainer)
        val correctContainer = view.findViewById<FrameLayout>(R.id.correctContainer)
        val incorrectContainer = view.findViewById<FrameLayout>(R.id.incorrectContainer)

        val correctAnswerText = incorrectContainer.findViewById<TextView>(R.id.correctAnswerText)

        view.findViewById<Button>(R.id.checkButton).setOnClickListener {
            val answersView = view.findViewById<RadioGroup>(R.id.answers)
            val selectedRadioId = answersView.checkedRadioButtonId
            val selectedRadio = view.findViewById<RadioButton>(selectedRadioId)
            val selectedAnswer = answersView.indexOfChild(selectedRadio)

            checkButtonContainer.visibility = View.GONE

            if (selectedAnswer == correctAnswer) {
                correctContainer.visibility = View.VISIBLE
            } else {
                val correctAnswerLetter = when (correctAnswer) {
                    0 -> "A"
                    1 -> "B"
                    2 -> "C"
                    3 -> "D"
                    else -> "Nie mo"
                }
                correctAnswerText.text = "Poprawna odpowiedź: ${correctAnswerLetter}"
                incorrectContainer.visibility = View.VISIBLE
            }
        }
    }
}