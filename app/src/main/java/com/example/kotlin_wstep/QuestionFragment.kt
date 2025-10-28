package com.example.kotlin_wstep

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.Fragment

interface OnNextQuestionListener {
    fun onNextQuestion(isCorrect: Boolean)
}

class QuestionFragment : Fragment(R.layout.fragment_question) {
    private var listener: OnNextQuestionListener? = null

    private var question: String? = null
    private var answers: Array<String>? = null
    private var correctAnswer: Int? = null
    private var imageId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnNextQuestionListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            question = it.getString("question")
            answers = it.getStringArray("answers")
            correctAnswer = it.getInt("correctAnswer")
            imageId = it.getInt("imageId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questionView = view.findViewById<TextView>(R.id.questionView)
        questionView.text = question

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        if (imageId == 0) imageView.visibility = View.GONE
        else imageView.setImageResource(
            resources.getIdentifier(
                "image_${imageId}",
                "drawable",
                context?.packageName
            )
        )

        val answersView = view.findViewById<RadioGroup>(R.id.answers)
        for (i in 0..3) {
            (answersView.getChildAt(i) as RadioButton).text = answers?.get(i)
        }

        val checkButtonContainer = view.findViewById<FrameLayout>(R.id.checkButtonContainer)
        val correctContainer = view.findViewById<LinearLayout>(R.id.correctContainer)
        val incorrectContainer = view.findViewById<LinearLayout>(R.id.incorrectContainer)

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

        view.findViewById<Button>(R.id.button_forward_correct).setOnClickListener {
            listener?.onNextQuestion(true)
        }

        view.findViewById<Button>(R.id.button_forward_incorrect).setOnClickListener {
            listener?.onNextQuestion(false)
        }
    }
}