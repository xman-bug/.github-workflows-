package com.example.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    
    private lateinit var tvDisplay: TextView
    private lateinit var calculator: Calculator
    
    private var currentInput = ""
    private var operator = ""
    private var firstOperand = 0.0
    private var isOperatorPressed = false
    private var isEqualsPressed = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        calculator = Calculator()
        tvDisplay = findViewById(R.id.tvDisplay)
        
        setupButtonListeners()
    }
    
    private fun setupButtonListeners() {
        // Number buttons
        val numberButtons = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        
        numberButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { view ->
                onNumberClick((view as Button).text.toString())
            }
        }
        
        // Operation buttons
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClick("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick("×") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick("÷") }
        
        // Special buttons
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualsClick() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btnDecimal).setOnClickListener { onDecimalClick() }
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener { onPlusMinusClick() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { onPercentClick() }
        findViewById<Button>(R.id.btnPrime).setOnClickListener { onPrimeClick() }
    }
    
    private fun onNumberClick(number: String) {
        if (isOperatorPressed || isEqualsPressed) {
            currentInput = ""
            isOperatorPressed = false
            isEqualsPressed = false
        }
        
        currentInput += number
        updateDisplay(currentInput)
    }
    
    private fun onOperatorClick(op: String) {
        if (currentInput.isNotEmpty()) {
            if (operator.isNotEmpty() && !isOperatorPressed) {
                onEqualsClick()
            }
            
            firstOperand = currentInput.toDoubleOrNull() ?: 0.0
            operator = op
            isOperatorPressed = true
            isEqualsPressed = false
        }
    }
    
    private fun onEqualsClick() {
        if (operator.isNotEmpty() && currentInput.isNotEmpty() && !isOperatorPressed) {
            val secondOperand = currentInput.toDoubleOrNull() ?: 0.0
            var result = 0.0
            
            try {
                when (operator) {
                    "+" -> result = calculator.add(firstOperand.toInt(), secondOperand.toInt()).toDouble()
                    "-" -> result = calculator.subtract(firstOperand.toInt(), secondOperand.toInt()).toDouble()
                    "×" -> result = calculator.multiply(firstOperand.toInt(), secondOperand.toInt()).toDouble()
                    "÷" -> {
                        if (secondOperand != 0.0) {
                            result = calculator.divide(firstOperand.toInt(), secondOperand.toInt())
                        } else {
                            showError("Cannot divide by zero")
                            return
                        }
                    }
                }
                
                currentInput = if (result == result.toInt().toDouble()) {
                    result.toInt().toString()
                } else {
                    String.format("%.6f", result).trimEnd('0').trimEnd('.')
                }
                
                updateDisplay(currentInput)
                operator = ""
                isEqualsPressed = true
                
            } catch (e: Exception) {
                showError("Error: ${e.message}")
            }
        }
    }
    
    private fun onClearClick() {
        currentInput = ""
        operator = ""
        firstOperand = 0.0
        isOperatorPressed = false
        isEqualsPressed = false
        updateDisplay("0")
    }
    
    private fun onDecimalClick() {
        if (isOperatorPressed || isEqualsPressed) {
            currentInput = "0"
            isOperatorPressed = false
            isEqualsPressed = false
        }
        
        if (currentInput.isEmpty()) {
            currentInput = "0"
        }
        
        if (!currentInput.contains(".")) {
            currentInput += "."
            updateDisplay(currentInput)
        }
    }
    
    private fun onPlusMinusClick() {
        if (currentInput.isNotEmpty() && currentInput != "0") {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            updateDisplay(currentInput)
        }
    }
    
    private fun onPercentClick() {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDoubleOrNull() ?: 0.0
            val result = value / 100
            currentInput = if (result == result.toInt().toDouble()) {
                result.toInt().toString()
            } else {
                String.format("%.6f", result).trimEnd('0').trimEnd('.')
            }
            updateDisplay(currentInput)
        }
    }
    
    private fun onPrimeClick() {
        if (currentInput.isNotEmpty()) {
            try {
                val number = currentInput.toIntOrNull()
                if (number != null) {
                    val isPrime = calculator.isPrime(number)
                    val message = if (isPrime) {
                        "$number is a prime number!"
                    } else {
                        "$number is not a prime number."
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                } else {
                    showError("Please enter a whole number to check for prime")
                }
            } catch (e: Exception) {
                showError("Invalid number for prime check")
            }
        } else {
            showError("Please enter a number first")
        }
    }
    
    private fun updateDisplay(text: String) {
        tvDisplay.text = if (text.isEmpty()) "0" else text
    }
    
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        onClearClick()
    }
}