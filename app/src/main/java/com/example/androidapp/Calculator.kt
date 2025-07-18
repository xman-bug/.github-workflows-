package com.example.androidapp

/**
 * A simple calculator utility class for demonstration purposes
 */
class Calculator {
    
    fun add(a: Int, b: Int): Int {
        return a + b
    }
    
    fun subtract(a: Int, b: Int): Int {
        return a - b
    }
    
    fun multiply(a: Int, b: Int): Int {
        return a * b
    }
    
    fun divide(a: Int, b: Int): Double {
        if (b == 0) {
            throw IllegalArgumentException("Division by zero is not allowed")
        }
        return a.toDouble() / b.toDouble()
    }
    
    fun isPrime(number: Int): Boolean {
        if (number <= 1) return false
        if (number == 2) return true
        if (number % 2 == 0) return false
        
        for (i in 3..kotlin.math.sqrt(number.toDouble()).toInt() step 2) {
            if (number % i == 0) return false
        }
        return true
    }
}