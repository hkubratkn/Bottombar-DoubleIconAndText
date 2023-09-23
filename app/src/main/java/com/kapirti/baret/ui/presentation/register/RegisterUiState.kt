package com.kapirti.baret.ui.presentation.register

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val button: Boolean = true
)