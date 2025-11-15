package com.example.kotlin_wstep

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import java.time.LocalDateTime

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = DatabaseProvider.getDatabase(applicationContext)

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

        val questionPercentage = (db.correctQuestionDao().getQuestions().size / 1281.0 * 100).toInt()
        findViewById<TextView>(R.id.questionPercentage).text = "${questionPercentage}%"
        val questionProgressBar = findViewById<ProgressBar>(R.id.questionProgressBar)
        questionProgressBar.progress = questionPercentage

        findViewById<Button>(R.id.startQuestionsButton)
            .setOnClickListener {
                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
            }
    }
}