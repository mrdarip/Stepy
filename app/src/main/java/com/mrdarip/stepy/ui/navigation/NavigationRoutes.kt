package com.mrdarip.stepy.ui.navigation

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
    companion object {
        fun fromTask(task: Task): ExecuteTaskRoute {
            return ExecuteTaskRoute(
                id = task.id,
                name = task.name
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
