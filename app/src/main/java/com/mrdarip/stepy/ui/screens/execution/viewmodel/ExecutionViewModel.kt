package com.mrdarip.stepy.ui.screens.execution.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrdarip.stepy.domain.model.Execution
import com.mrdarip.stepy.domain.model.Step
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

    private val _steps = MutableStateFlow<List<Step>>(emptyList())
    val steps: StateFlow<List<Step>> = _steps

    private val _currentExecution = MutableStateFlow<Execution?>(null)

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
            _steps.value = taskRepository.getStepsOfTask(taskId)

            if (_steps.value.isNotEmpty()) {
                _currentExecution.value = executionRepository.createExecutionNow(
                    step = _steps.value.first()
                )
            }
        }
    }

    fun completeStep(step: Step, onFinish: () -> Unit) {
        _steps.value = _steps.value.filter { it != step }

        viewModelScope.launch {
            executionRepository.addExecution(
                _currentExecution.value!!
            )
        }

        if (_steps.value.isEmpty()) {
            onFinish()
        }
    }
}