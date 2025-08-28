package com.mrdarip.stepy.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mrdarip.stepy.ui.screens.execution.ExecutionScreen
import com.mrdarip.stepy.ui.screens.home.HomeScreen

@Composable
fun StepyNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        composable<HomeRoute> { backStackEntry ->
            HomeScreen(
                onExecuteTaskClick = { task ->
                    Log.d("HomeScreen", "onExecuteTaskClick: $task")
                    navController.navigate(ExecuteTaskRoute.fromTask(task))
                }
            )
        }

        composable<ExecuteTaskRoute> { backStackEntry ->
            Log.d("StepyNavigation", "composable: ${backStackEntry.arguments}")
            val taskToExecute = ExecuteTaskRoute.fromBackStackEntry(backStackEntry)
            if (taskToExecute == null) return@composable

            ExecutionScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                taskToExecute = taskToExecute.toTask()
            )
        }
    }
}
