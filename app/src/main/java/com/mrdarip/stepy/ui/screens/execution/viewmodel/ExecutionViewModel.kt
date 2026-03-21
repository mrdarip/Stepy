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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Load Route params
    private val _taskId: Int = checkNotNull(savedStateHandle["taskId"]) {
        "_taskId is required for ExecutionViewModel"
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
            _stepsWithStats.value = taskRepository.getStepsAndStatsOfTask(taskId, 50)

            if (_stepsWithStats.value.isNotEmpty()) {
                _currentExecution.value = executionRepository.createExecutionNow(
                    step = _stepsWithStats.value.first().step
                )
            }
        }
    }

    fun completeExecution(onFinish: () -> Unit) {
        _stepsWithStats.value = _stepsWithStats.value.drop(1)

        val completedExecution = _currentExecution.value!!.copy(
            end = System.currentTimeMillis() / 1000L
        )

        viewModelScope.launch {
            val lastExecutionId = executionRepository.addExecution(
                completedExecution
            )

            if (_stepsWithStats.value.isNotEmpty()) {
                _currentExecution.value = executionRepository.createExecutionNow(
                    step = _stepsWithStats.value.first().step,
                    parentExecution = completedExecution.copy(id = lastExecutionId)
                )
            }
        }

        if (_stepsWithStats.value.isEmpty()) {
            onFinish()
        }
    }
}