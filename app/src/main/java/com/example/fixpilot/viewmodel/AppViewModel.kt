package com.example.fixpilot.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fixpilot.data.PreferenceHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class AppViewModel(private val preferenceHelper: PreferenceHelper) : ViewModel() {

    // Dark Mode Zustand (optional, falls du es benutzt)
    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled.asStateFlow()

    // Sprache (optional)
    private val _languageCode = MutableStateFlow("de")
    val languageCode: StateFlow<String> = _languageCode.asStateFlow()

    // Fact Index
    private val _currentFactIndex = MutableStateFlow(0)
    val currentFactIndex: StateFlow<Int> = _currentFactIndex.asStateFlow()

    fun setDarkModeEnabled(enabled: Boolean) {
        _darkModeEnabled.value = enabled
    }

    fun setLanguage(code: String) {
        _languageCode.value = code
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
