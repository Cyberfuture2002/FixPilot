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
import androidx.compose.ui.res.stringResource
import com.example.fixpilot.ui.screens.feedback.FeedbackScreen
import com.example.fixpilot.R

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
                    label = { Text(stringResource(R.string.nav_label_start)) },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Start") }
                )

                NavigationBarItem(
                    selected = currentRoute == "questionFlow",
                    onClick = { navController.navigate("questionFlow") { launchSingleTop = true } },
                    label = { Text(stringResource(R.string.nav_label_question)) },
                    icon = { Icon(Icons.Filled.Help, contentDescription = "Fragen") }
                )
                NavigationBarItem(
                    selected = currentRoute == "errorDatabase",
                    onClick = { navController.navigate("errorDatabase") { launchSingleTop = true } },
                    label = { Text(stringResource(R.string.nav_label_error)) },
                    icon = { Icon(Icons.Filled.Storage, contentDescription = "Fehler") }
                )
                NavigationBarItem(
                    selected = currentRoute == "settings",
                    onClick = { navController.navigate("settings") { launchSingleTop = true } },
                    label = { Text(stringResource(R.string.nav_label_menu)) },
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
                HomeScreen(navController, appViewModel = viewModel)
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
