package com.example.fixpilot.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    // Dark Mode Zustand
    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled.asStateFlow()

    // Aktuelle Sprache
    private val _languageCode = MutableStateFlow("de")
    val languageCode: StateFlow<String> = _languageCode.asStateFlow()

    fun setDarkModeEnabled(enabled: Boolean) {
        _darkModeEnabled.value = enabled
    }


    // Setter für Sprache
    fun setLanguage(code: String) {
        _languageCode.value = code
    }
}
