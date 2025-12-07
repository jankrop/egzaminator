package com.example.egzaminator

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.egzaminator.api.APIClient
import com.example.egzaminator.api.MistralAPIMessage
import com.example.egzaminator.api.MistralAPIRequest
import kotlinx.coroutines.launch

interface OnNextQuestionListener {
    fun onNextQuestion(id: Int?, isCorrect: Boolean)
}

class QuestionFragment : Fragment(R.layout.fragment_question) {
    private var listener: OnNextQuestionListener? = null

    private var questionId: Int? = null
    private var question: String? = null
    private var answers: Array<String>? = null
    private var correctAnswer: Int? = null
    private var imageId: Int? = null
    private var selectedAnswer: Int? = null

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
            questionId = it.getInt("questionId")
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

        val imageContainer = view.findViewById<HorizontalScrollView>(R.id.imageContainer)
        val imageView = view.findViewById<ImageView>(R.id.questionImage)
        if (imageId == 0) imageContainer.visibility = View.GONE
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
            selectedAnswer = answersView.indexOfChild(selectedRadio)

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
            listener?.onNextQuestion(questionId, true)
        }

        view.findViewById<Button>(R.id.button_forward_incorrect).setOnClickListener {
            listener?.onNextQuestion(questionId, false)
        }

        val askAIButton = view.findViewById<Button>(R.id.button_ask_ai)
        if (imageId == 0) askAIButton.visibility = View.VISIBLE

        askAIButton.setOnClickListener {
            askAIButton.text = "Ładowanie..."
            askAIButton.isEnabled = false

            lifecycleScope.launch {
                try {
                    val body = MistralAPIRequest(
                        messages = arrayOf(
                            MistralAPIMessage(
                                "system",
                                "Jesteś użyty w aplikacji do uczenia się do egzaminu o " +
                                        "podstawach web developmentu (HTML, CSS, JS, PHP) oraz " +
                                        "bazach danych. Dostaniesz pytanie, poprawną odpowiedź i " +
                                        "błędną odpowiedź użytkownika. Powiedz, czemu odpowiedź " +
                                        "użytkownika jest błędna, i czemu poprawna odpowiedź jest " +
                                        "poprawna. Używaj krótkich wypowiedzi i załóż, że " +
                                        "użytkownik jest początkujący w temacie. Nie używaj " +
                                        "Markdown."
                            ),
                            MistralAPIMessage(
                                "user",
                                "Mam pytanie: \"$question\". Poprawna odpowiedź: \"${
                                    answers?.get(
                                        correctAnswer ?: 0
                                    )
                                }\". Moja odpowiedź: \"${answers?.get(selectedAnswer ?: 0)}\"."
                            )
                        )
                    )

                    Log.d("API", body.toString())

                    val apiResponse = APIClient.api.getCompletions(
                        "Bearer ${BuildConfig.API_KEY}", body
                    )

                    val message = apiResponse.choices[0].message.content

                    val builder = AlertDialog.Builder(requireContext())
                    builder
                        .setTitle("Zapytaj AI")
                        .setMessage("Poprawna odpowiedź: \"${answers?.get(correctAnswer ?: 0)}\"\n" +
                                "Moja odpowiedź: \"${answers?.get(selectedAnswer ?: 0)}\"\n\n" +
                                message)
                        .setPositiveButton("Dzięki!") { dialog, which -> }
                    val dialog = builder.create()
                    dialog.show()
                    askAIButton.text = "Zapytaj AI"
                    askAIButton.isEnabled = true
                } catch (e: Exception) {
                    Log.e("API", "Error", e)
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Zapytaj AI").setMessage("Wystąpił błąd (${e.message})").setPositiveButton("OK") { dialog, which -> }
                    val dialog = builder.create()
                    dialog.show()
                    askAIButton.text = "Zapytaj AI"
                    askAIButton.isEnabled = true
                }
            }
        }
    }
}