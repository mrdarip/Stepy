package com.mrdarip.stepy.ui.screens.execution.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrdarip.stepy.domain.model.Execution
import com.mrdarip.stepy.domain.model.StepWithStats
import com.mrdarip.stepy.domain.model.Task
import com.mrdarip.stepy.domain.repository.ExecutionRepository
import com.mrdarip.stepy.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExecutionViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val executionRepository: ExecutionRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Load Route params
    private val _taskId: Int = checkNotNull(savedStateHandle["taskId"]) {
        "_taskId is required for ExecutionViewModel"
    }

    // count of completed steps, stored for surviving process death
    private var completedStepsCount: Int
        get() = savedStateHandle["completed_count"] ?: 0
        set(value) {
            savedStateHandle["completed_count"] = value
        }

    // execution start time, stored for surviving process death
    private var executingStepStart: Long
        get() = savedStateHandle["executing_step_start"] ?: 0
        set(value) {
            savedStateHandle["executing_step_start"] = value
        }


    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task.asStateFlow()

    private val _stepsWithStats = MutableStateFlow<List<StepWithStats>>(emptyList())
    val steps: StateFlow<List<StepWithStats>> = _stepsWithStats

    private val _currentExecution = MutableStateFlow<Execution?>(null)
    val currentExecution: StateFlow<Execution?> = _currentExecution

    init {
        loadTask(_taskId)
        loadSteps(_taskId)
    }

    private fun loadTask(taskId: Int) {
        viewModelScope.launch {
            _task.value = taskRepository.getTask(taskId)
        }
    }

    private fun loadSteps(taskId: Int) {
        viewModelScope.launch {
            val allSteps = taskRepository.getStepsAndStatsOfTask(taskId, 20)
            val remainingSteps = allSteps.drop(completedStepsCount)
            _stepsWithStats.value = remainingSteps

            // exit coroutine if no step to execute
            val firstStep = remainingSteps.firstOrNull() ?: return@launch

            // Restore start time if it exists, otherwise use "now"
            val startTime = if (executingStepStart > 0L) {
                executingStepStart
            } else {
                System.currentTimeMillis() / 1000L
            }

            _currentExecution.value = executionRepository.createExecutionNow(
                step = firstStep.step
            ).copy(start = startTime)
        }
    }

    fun completeExecution(onFinish: () -> Unit) {
        val currentExec = _currentExecution.value ?: return // Guard against null

        completedStepsCount++
        val remaining = _stepsWithStats.value.drop(1)
        _stepsWithStats.value = remaining

        val completedExecution = currentExec.copy(
            end = System.currentTimeMillis() / 1000L
        )

        viewModelScope.launch {
            val lastExecutionId = executionRepository.addExecution(completedExecution)

            if (remaining.isNotEmpty()) {
                val nextStartTime = System.currentTimeMillis() / 1000L
                executingStepStart = nextStartTime // Save for next potential process death

                _currentExecution.value = executionRepository.createExecutionNow(
                    step = remaining.first().step,
                    parentExecution = completedExecution.copy(id = lastExecutionId)
                ).copy(start = nextStartTime)
            } else {
                completedStepsCount = 0 // Reset for next execution
                executingStepStart = 0L // Reset start time
                _currentExecution.value = null
                onFinish()
            }
        }
    }
}