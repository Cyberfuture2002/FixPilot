package com.example.fixpilot.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fixpilot.data.PreferenceHelper

class AppViewModelFactory(
    private val preferenceHelper: PreferenceHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(preferenceHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
