package com.example.fixpilot.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LanguageManager {

    fun setLanguage(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }

        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // Sprache speichern
        context.getSharedPreferences("fixpilot_prefs", Context.MODE_PRIVATE)
            .edit()
            .putString("language", languageCode)
            .apply()
    }

    fun getCurrentLanguage(context: Context): String {
        return context.getSharedPreferences("fixpilot_prefs", Context.MODE_PRIVATE)
            .getString("language", "de") ?: "de"
    }
}
fun wrapContextWithLanguage(context: Context, languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    } else {
        @Suppress("DEPRECATION")
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        return context
    }
}
