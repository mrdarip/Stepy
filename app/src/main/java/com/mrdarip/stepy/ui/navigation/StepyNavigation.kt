package com.mrdarip.stepy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mrdarip.stepy.ui.screens.details.DetailsScreen
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
                    navController.navigate(TaskDetailRoute.fromTask(task))
                }
            )
        }

        composable<ExecuteTaskRoute> { backStackEntry ->
            ExecutionScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                onFinish = {
                    navController.navigate(HomeRoute)
                }
            )
        }

        composable<TaskDetailRoute> { backStackEntry ->
            DetailsScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                onExecuteTaskClick = { task ->
                    navController.navigate(ExecuteTaskRoute.fromTask(task))
                },
                onEditTaskClick = { task ->
                    TODO()
                }
            )
        }
    }
}
