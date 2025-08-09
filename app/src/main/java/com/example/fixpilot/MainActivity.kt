package com.example.fixpilot

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.fixpilot.ui.MainScreen
import com.example.fixpilot.util.LanguageManager
import com.example.fixpilot.viewmodel.AppViewModel
import com.example.fixpilot.ui.theme.FixPilotTheme
import java.util.Locale
import android.content.res.Configuration
import android.os.Build
import com.example.fixpilot.data.PreferenceHelper
import androidx.lifecycle.ViewModelProvider
import com.example.fixpilot.viewmodel.AppViewModelFactory


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: AppViewModel

    override fun attachBaseContext(newBase: Context) {
        val language = LanguageManager.getCurrentLanguage(newBase)
        val context = updateLocale(newBase, language)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = PreferenceHelper(this)
        val factory = AppViewModelFactory(prefs)
        viewModel = ViewModelProvider(this, factory).get(AppViewModel::class.java)

        setContent {
            val darkMode by viewModel.darkModeEnabled.collectAsState()
            val navController = rememberNavController()

            FixPilotTheme(darkTheme = darkMode) {
                MainScreen(navController = navController, viewModel = viewModel)
            }
        }
    }

    private fun updateLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            context.createConfigurationContext(config)
        } else {
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }
}
