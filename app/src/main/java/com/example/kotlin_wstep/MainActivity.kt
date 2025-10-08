package com.example.kotlin_wstep

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val questionView = findViewById<TextView>(R.id.question)
        questionView.text = when ((0..2).random()) {
            0 -> "Jak długą trzeba mieć brodę żeby zostać sysadminem?"
            1 -> "Jak napisać pętlę for w HTMLu?"
            2 -> "Dlaczego JavaScript nas zachwyca?"
            else -> "Nie znaleziono pytania"
        }
    }
}