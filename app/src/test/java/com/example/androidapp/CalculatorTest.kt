package com.example.androidapp

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Unit tests for Calculator class
 */
class CalculatorTest {
    
    private lateinit var calculator: Calculator
    
    @Before
    fun setUp() {
        calculator = Calculator()
    }
    
    @Test
    fun testAddition() {
        assertEquals(5, calculator.add(2, 3))
        assertEquals(0, calculator.add(-5, 5))
        assertEquals(-8, calculator.add(-3, -5))
        assertEquals(100, calculator.add(45, 55))
    }
    
    @Test
    fun testSubtraction() {
        assertEquals(1, calculator.subtract(3, 2))
        assertEquals(-10, calculator.subtract(-5, 5))
        assertEquals(2, calculator.subtract(-3, -5))
        assertEquals(0, calculator.subtract(50, 50))
    }
    
    @Test
    fun testMultiplication() {
        assertEquals(6, calculator.multiply(2, 3))
        assertEquals(0, calculator.multiply(0, 5))
        assertEquals(-15, calculator.multiply(-3, 5))
        assertEquals(25, calculator.multiply(-5, -5))
    }
    
    @Test
    fun testDivision() {
        assertEquals(2.0, calculator.divide(6, 3), 0.0001)
        assertEquals(2.5, calculator.divide(5, 2), 0.0001)
        assertEquals(-2.0, calculator.divide(-6, 3), 0.0001)
        assertEquals(0.5, calculator.divide(1, 2), 0.0001)
    }
    
    @Test(expected = IllegalArgumentException::class)
    fun testDivisionByZero() {
        calculator.divide(5, 0)
    }
    
    @Test
    fun testIsPrime() {
        // Test prime numbers
        assertTrue(calculator.isPrime(2))
        assertTrue(calculator.isPrime(3))
        assertTrue(calculator.isPrime(5))
        assertTrue(calculator.isPrime(7))
        assertTrue(calculator.isPrime(11))
        assertTrue(calculator.isPrime(13))
        assertTrue(calculator.isPrime(17))
        assertTrue(calculator.isPrime(19))
        assertTrue(calculator.isPrime(23))
        
        // Test non-prime numbers
        assertFalse(calculator.isPrime(1))
        assertFalse(calculator.isPrime(4))
        assertFalse(calculator.isPrime(6))
        assertFalse(calculator.isPrime(8))
        assertFalse(calculator.isPrime(9))
        assertFalse(calculator.isPrime(10))
        assertFalse(calculator.isPrime(12))
        assertFalse(calculator.isPrime(15))
        assertFalse(calculator.isPrime(16))
        assertFalse(calculator.isPrime(20))
        
        // Edge cases
        assertFalse(calculator.isPrime(0))
        assertFalse(calculator.isPrime(-1))
        assertFalse(calculator.isPrime(-5))
    }
}