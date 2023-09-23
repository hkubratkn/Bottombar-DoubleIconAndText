package com.kapirti.baret.ui.presentation.log_in

data class LogInUiState(
    val email: String = "",
    val password: String = "",
    val button: Boolean = true
)