package com.example.fixpilot.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fixpilot.data.PreferenceHelper
import com.example.fixpilot.ui.screens.einstellungen.EinstellungenScreen
import com.example.fixpilot.ui.screens.errordatabase.ErrorDatabaseScreen
import com.example.fixpilot.ui.screens.feedback.FeedbackScreen
import com.example.fixpilot.ui.screens.home.HomeScreen
import com.example.fixpilot.ui.screens.questionflow.FragebaumScreen
import com.example.fixpilot.ui.screens.solution.SolutionScreen
import com.example.fixpilot.viewmodel.AppViewModel
import com.example.fixpilot.ui.splash.SplashScreen
import com.example.fixpilot.ui.screens.legal.LegalScreen

@Composable
fun FixPilotNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel,
    prefs: PreferenceHelper
) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onFinished = { navController.navigate("home") }
            )
        }
        composable("home") {
            HomeScreen(navController, prefs = prefs)
        }
        composable("questionFlow") {
            FragebaumScreen(navController, viewModel)
        }
        composable("errorDatabase") {
            ErrorDatabaseScreen(navController)
        }
        composable("feedback") {
           FeedbackScreen(navController)
        }
        composable("settings") {
            EinstellungenScreen(viewModel = viewModel, navController = navController)
        }
        composable("legal") {
            LegalScreen(navController = navController)
        }

        composable("solution/{solutionId}") { backStackEntry ->
            val solutionId = backStackEntry.arguments?.getString("solutionId")
            if (solutionId == null) {
                // Alternative: Fehleranzeige, oder zurück zu questionFlow
                navController.navigate("questionFlow") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                    launchSingleTop = true
                }
            } else {
                SolutionScreen(solutionId, navController)
            }
        }



    }

}
