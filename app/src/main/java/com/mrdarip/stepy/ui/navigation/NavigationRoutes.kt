package com.mrdarip.stepy.ui.navigation

import android.util.Log
import androidx.navigation.NavBackStackEntry
import com.mrdarip.stepy.domain.model.Routine
import com.mrdarip.stepy.domain.model.Task
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class ExecuteTaskRoute(
    val id: Int,
    val name: String
) {


    fun toTask(): Task {
        return Task(
            id = this.id,
            name = this.name
        )
    }
    companion object {
        fun fromTask(task: Task): ExecuteTaskRoute {
            return ExecuteTaskRoute(
                id = task.id,
                name = task.name
            )
        }

        fun fromBackStackEntry(backStackEntry: NavBackStackEntry): ExecuteTaskRoute? {
            val name = backStackEntry.arguments?.getString("name")
            val id = backStackEntry.arguments?.getInt("id")

            Log.d("ExecuteTaskRoute", "fromBackStackEntry: $name, $id")

            if (name == null || id == null) return null

            return ExecuteTaskRoute(
                id = id,
                name = name
            )
        }
    }
}

@Serializable
data class ExecuteRoutineRoute(
    val id: Int,
    val name: String
) {
    companion object {
        fun fromRoutine(routine: Routine): ExecuteRoutineRoute {
            return ExecuteRoutineRoute(
                id = routine.id,
                name = routine.name
            )
        }
    }
}
