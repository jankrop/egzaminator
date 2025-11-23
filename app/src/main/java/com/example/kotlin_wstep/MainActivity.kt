package com.example.kotlin_wstep

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    fun updateData() {
        val db = DatabaseProvider.getDatabase(applicationContext)

        val questionPercentage = (db.correctQuestionDao().getQuestions().size / 1281.0 * 100).toInt()
        findViewById<TextView>(R.id.questionPercentage).text = "$questionPercentage%"
        val questionProgressBar = findViewById<ProgressBar>(R.id.questionProgressBar)
        questionProgressBar.progress = questionPercentage

        val averageScore = (db.attemptDao().loadAllAttempts().map { it.correctAnswers }.average() / 40.0 * 100).toInt()
        findViewById<TextView>(R.id.averageScore).text = "${averageScore}%"
        val scoreProgressBar = findViewById<ProgressBar>(R.id.scoreProgressBar)
        scoreProgressBar.progress = averageScore
        scoreProgressBar.progressTintList = ColorStateList.valueOf(when (averageScore) {
            in 0..<50 -> Color.rgb(244, 67, 54)
            in 50..<75 -> Color.rgb(255, 193, 7)
            else -> Color.rgb(76, 175, 80)
        })

        findViewById<TextView>(R.id.welcomeMessage).text =
            if (LocalDateTime.now().hour in 6..22) (
                    arrayOf(
                        "Jak leci?",
                        "Gotowy?",
                        "Witaj!",
                        "Cześć i czołem!",
                        "<h1><big>Cześć!</big></h1>",
                        when (LocalDateTime.now().hour) {
                            in 6..17 -> "Dzień dobry!"
                            in 18..22 -> "Dobry wieczór!"
                            else -> ""
                        },
                        "01110011 01101001 01100101 01101101 01100001",
                    ).random()
                    ) else "O tej porze? Wyluzuj!"

        findViewById<ListView>(R.id.attemptsList).adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            db.attemptDao().loadAllAttempts().map {
                val date = LocalDateTime.parse(it.createdAt)
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                "${date.format(formatter)}: ${it.correctAnswers * 100 / 40}%"
            }
        )
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        updateData()

        findViewById<Button>(R.id.startQuestionsButton)
            .setOnClickListener {
                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
            }
    }
}