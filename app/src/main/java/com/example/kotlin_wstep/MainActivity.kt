package com.example.kotlin_wstep

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        findViewById<Button>(R.id.startQuestionsButton)
            .setOnClickListener {
                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
            }
    }
}