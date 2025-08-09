package com.example.fixpilot.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fixpilot.data.PreferenceHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class AppViewModel(private val preferenceHelper: PreferenceHelper) : ViewModel() {

    // Dark Mode Zustand
    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled.asStateFlow()

    // Sprache
    private val _languageCode = MutableStateFlow("de")
    val languageCode: StateFlow<String> = _languageCode.asStateFlow()

    // Aktueller Fact Index
    private val _currentFactIndex = MutableStateFlow(0)
    val currentFactIndex: StateFlow<Int> = _currentFactIndex.asStateFlow()

    // Anzeige der Tech-Facts (dauerhaft, Ein/Aus)
    private val _showTechFact = MutableStateFlow(true)
    val showTechFact: StateFlow<Boolean> = _showTechFact.asStateFlow()

    init {
        // Initiale Werte laden
        _darkModeEnabled.value = preferenceHelper.loadDarkModeEnabled()
        _languageCode.value = preferenceHelper.loadLanguage()
        _showTechFact.value = preferenceHelper.loadShowTechFact()
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        _darkModeEnabled.value = enabled
        preferenceHelper.saveDarkModeEnabled(enabled)
    }

    fun setLanguage(code: String) {
        _languageCode.value = code
        preferenceHelper.saveLanguage(code)
    }

    fun setShowTechFact(show: Boolean) {
        _showTechFact.value = show
        preferenceHelper.saveShowTechFact(show)
    }

    fun loadDailyFactIndex(factCount: Int) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val lastDate = preferenceHelper.loadLastFactShownDate()
        var lastIndex = preferenceHelper.loadLastFactIndex()

        if (lastDate != today) {
            // Neuer Tag -> nächster Fact
            lastIndex = (lastIndex + 1) % factCount
            preferenceHelper.saveLastFactShownDate(today)
            preferenceHelper.saveLastFactIndex(lastIndex)
        }
        _currentFactIndex.value = lastIndex.coerceAtLeast(0)
    }
}
