package com.example.fixpilot.data

import android.content.Context

class PreferenceHelper(context: Context) {

    private val prefs = context.getSharedPreferences("fixpilot_prefs", Context.MODE_PRIVATE)

    // Dark Mode
    fun saveDarkModeEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("dark_mode_enabled", enabled).apply()
    }
    fun loadDarkModeEnabled(): Boolean {
        return prefs.getBoolean("dark_mode_enabled", false)
    }
    // Frage: Wurde der Fragebaum gestartet?
    fun loadQuestionFlowStarted(): Boolean {
        return prefs.getBoolean("question_flow_started", false)
    }

    fun saveQuestionFlowStarted(started: Boolean) {
        prefs.edit().putBoolean("question_flow_started", started).apply()
    }

    // Soll Erklärung im Fragebaum angezeigt werden?
    fun loadShowExplanation(): Boolean {
        return prefs.getBoolean("show_explanation", true) // default true
    }

    fun saveShowExplanation(show: Boolean) {
        prefs.edit().putBoolean("show_explanation", show).apply()
    }

    // Sprache
    fun saveLanguage(languageCode: String) {
        prefs.edit().putString("language_code", languageCode).apply()
    }
    fun loadLanguage(): String {
        return prefs.getString("language_code", "de") ?: "de"
    }

    // TechFact Anzeige (dauerhaft)
    fun saveShowTechFact(show: Boolean) {
        prefs.edit().putBoolean("show_tech_fact", show).apply()
    }
    fun loadShowTechFact(): Boolean {
        return prefs.getBoolean("show_tech_fact", true)
    }

    // Letzter Fact und Datum für Tageslogik
    fun saveLastFactShownDate(date: String) {
        prefs.edit().putString("last_fact_shown_date", date).apply()
    }
    fun loadLastFactShownDate(): String? {
        return prefs.getString("last_fact_shown_date", null)
    }

    fun saveLastFactIndex(index: Int) {
        prefs.edit().putInt("last_fact_index", index).apply()
    }
    fun loadLastFactIndex(): Int {
        return prefs.getInt("last_fact_index", -1)
    }
}
