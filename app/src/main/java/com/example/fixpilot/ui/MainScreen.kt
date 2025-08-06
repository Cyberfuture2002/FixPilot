package com.example.fixpilot.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fixpilot.ui.screens.einstellungen.EinstellungenScreen
import com.example.fixpilot.ui.screens.errordatabase.ErrorDatabaseScreen
import com.example.fixpilot.ui.screens.home.HomeScreen
import com.example.fixpilot.ui.screens.questionflow.FragebaumScreen
import com.example.fixpilot.ui.screens.solution.SolutionScreen
import com.example.fixpilot.util.LanguageManager
import com.example.fixpilot.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.fixpilot.ui.screens.feedback.FeedbackScreen

@Composable
fun MainScreen(navController: NavHostController, viewModel: AppViewModel) {
    val context = LocalContext.current
    val languageCode = LanguageManager.getCurrentLanguage(context)

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = { navController.navigate("home") { launchSingleTop = true } },
                    label = { Text("Start") },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Start") }
                )

                NavigationBarItem(
                    selected = currentRoute == "questionFlow",
                    onClick = { navController.navigate("questionFlow") { launchSingleTop = true } },
                    label = { Text("Fragen") },
                    icon = { Icon(Icons.Filled.Help, contentDescription = "Fragen") }
                )
                NavigationBarItem(
                    selected = currentRoute == "errorDatabase",
                    onClick = { navController.navigate("errorDatabase") { launchSingleTop = true } },
                    label = { Text("Fehler") },
                    icon = { Icon(Icons.Filled.Storage, contentDescription = "Fehler") }
                )
                NavigationBarItem(
                    selected = currentRoute == "settings",
                    onClick = { navController.navigate("settings") { launchSingleTop = true } },
                    label = { Text("Menü") },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Menü") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(navController)
            }
            composable("questionFlow") {
                FragebaumScreen(navController, viewModel)
            }
            composable("errorDatabase") {
                ErrorDatabaseScreen(navController)
            }
            composable ("feedback"){
                FeedbackScreen(navController)
            }
            composable("settings") {
                EinstellungenScreen(viewModel = viewModel, navController = navController)
            }
            composable("solution/{solutionId}") { backStackEntry ->
                val solutionId = backStackEntry.arguments?.getString("solutionId") ?: return@composable
                SolutionScreen(solutionId, navController)
            }
        }
    }
}
