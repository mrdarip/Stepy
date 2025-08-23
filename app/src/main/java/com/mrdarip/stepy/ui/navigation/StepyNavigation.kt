package com.mrdarip.stepy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mrdarip.stepy.ui.screens.home.HomeScreen
import com.mrdarip.stepy.ui.screens.sample.TestScreen

@Composable
fun StepyNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.route,
        modifier = modifier
    ) {
        composable(AppScreen.Home.route) {
            HomeScreen(
                onNavigateToDetails = {
                    navController.navigate(AppScreen.Test.route)
                }
            )
        }
        
        composable(AppScreen.Test.route) {
            TestScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
