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

    fun saveLastFactShownDate(date: String) {
        prefs.edit().putString("last_fact_date", date).apply()
    }

    fun loadLastFactShownDate(): String? {
        return prefs.getString("last_fact_date", null)
    }

    fun saveLastFactIndex(index: Int) {
        prefs.edit().putInt("last_fact_index", index).apply()
    }

    fun loadLastFactIndex(): Int {
        return prefs.getInt("last_fact_index", -1)
    }
}
