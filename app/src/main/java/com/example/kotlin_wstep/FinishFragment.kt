package com.example.kotlin_wstep

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class FinishFragment : Fragment(R.layout.fragment_finish) {
    private var correct: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            correct = it.getInt("correct")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val percentage = ((correct ?: 0) / 40.0 * 100).toInt()
        val endText = when (percentage) {
            in 0..<50 -> "Próbuj dalej!"
            in 50..<55 -> "Mało brakowało!"
            in 55..<75 -> "Nie jest źle!"
            in 75..<90 -> "Dobra robota!"
            in 90..<100 -> "Gratulacje!"
            100 -> "Zasłużyłeś/aś na odpoczynek!"
            else -> ""
        }

        view.findViewById<TextView>(R.id.endText).text = endText
        view.findViewById<TextView>(R.id.percentage).text = "${percentage}%"

        val endProgressBar = view.findViewById<ProgressBar>(R.id.endProgressBar)

        endProgressBar.progress = percentage
        endProgressBar.progressTintList = ColorStateList.valueOf(when (percentage) {
            in 0..<50 -> Color.rgb(244, 67, 54)
            in 50..<75 -> Color.rgb(255, 193, 7)
            else -> Color.rgb(76, 175, 80)
        })
    }
}