package com.example.fixpilot.data

import android.content.Context

class PreferenceHelper(context: Context) {
    private val prefs = context.getSharedPreferences("fixpilot_prefs", Context.MODE_PRIVATE)

    fun saveShowExplanation(show: Boolean) {
        prefs.edit().putBoolean("show_explanation", show).apply()
    }

    fun loadShowExplanation(): Boolean {
        return prefs.getBoolean("show_explanation", true)
    }

    fun saveQuestionFlowStarted(started: Boolean) {
        prefs.edit().putBoolean("question_flow_started", started).apply()
    }

    fun loadQuestionFlowStarted(): Boolean {
        return prefs.getBoolean("question_flow_started", false)
    }

    fun saveLanguage(languageCode: String) {
        prefs.edit().putString("language_code", languageCode).apply()
    }

    fun loadLanguage(): String {
        return prefs.getString("language_code", "de") ?: "de"
    }
}
