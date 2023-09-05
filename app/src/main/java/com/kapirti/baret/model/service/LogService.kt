package com.kapirti.baret.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}