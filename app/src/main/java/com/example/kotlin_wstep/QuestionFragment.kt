package com.example.kotlin_wstep

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuestionFragment : Fragment(R.layout.fragment_question) {
    private var questionId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            questionId = it.getInt("questionId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questionView = view.findViewById<TextView>(R.id.questionView)
        questionView.text = when ((0..2).random()) {
            0 -> "Jak długą trzeba mieć brodę żeby zostać sysadminem?"
            1 -> "Jak napisać pętlę for w HTMLu?"
            2 -> "Dlaczego JavaScript nas zachwyca?"
            else -> "Nie znaleziono pytania"
        }
    }
}