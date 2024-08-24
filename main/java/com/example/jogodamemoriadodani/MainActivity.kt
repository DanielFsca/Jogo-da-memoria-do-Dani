package com.example.jogodamemoriadodani

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var resetButton: Button
    private val buttons = mutableListOf<Button>()
    private val cardValues = mutableListOf<Int>()
    private var firstButton: Button? = null
    private var secondButton: Button? = null
    private lateinit var scoreTextView: TextView
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        resetButton = findViewById(R.id.resetButton)
        scoreTextView = findViewById(R.id.scoreTextView)

        setupGame()

        resetButton.setOnClickListener { setupGame() }
    }

    private fun setupGame() {
        cardValues.clear()
        buttons.clear()
        gridLayout.removeAllViews()

        // Gerando pares de valores para os botões e embaralhar
        val values = (1..6).flatMap { listOf(it, it) }.shuffled()
        cardValues.addAll(values)

        for (i in 0 until 12) {
            val button = Button(this)
            button.text = ""
            button.setOnClickListener { onButtonClick(button, i) }
            buttons.add(button)
            gridLayout.addView(button, GridLayout.LayoutParams().apply {
                width = 0
                height = 0
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            })
        }
    }


    private fun onButtonClick(button: Button, index: Int) {
        if (firstButton == null) {
            firstButton = button
            button.text = cardValues[index].toString()
        } else if (secondButton == null) {
            secondButton = button
            button.text = cardValues[index].toString()
            checkMatch()
        }
    }

    private fun checkMatch() {
        if (firstButton != null && secondButton != null) {
            val firstIndex = buttons.indexOf(firstButton)
            val secondIndex = buttons.indexOf(secondButton)
            if (cardValues[firstIndex] == cardValues[secondIndex]) {
                firstButton?.isEnabled = false
                secondButton?.isEnabled = false
            } else {
                firstButton?.postDelayed({
                    firstButton?.text = ""
                    secondButton?.text = ""
                }, 500)
            }
            firstButton = null
            secondButton = null
        }
        if (buttons.all { !it.isEnabled }) {
            Toast.makeText(this, "Boa! Você achou todos os pares.", Toast.LENGTH_LONG).show()
        }
    }
}
