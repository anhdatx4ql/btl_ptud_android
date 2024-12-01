package com.example.btl_ptud_android

import org.junit.Assert.*
import org.junit.Test

class LoginActivityTest {

    @Test
    fun testPasswordMatch() {
        val password1 = "123456"
        val password2 = "123456"
        assertEquals("Passwords should match", password1, password2)
    }

    @Test
    fun testEmailValidation() {
        val email = "test@gmail.com"
        assertTrue("Email should be valid", email.contains("@") && email.contains("."))
    }

    @Test
    fun testInvalidEmail() {
        val email = "invalidEmail"
        assertFalse("Email should be invalid", email.contains("@"))
    }
}
