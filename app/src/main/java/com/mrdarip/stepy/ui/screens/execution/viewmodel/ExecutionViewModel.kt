package com.mrdarip.stepy.ui.screens.execution.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.model.Task
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val taskId: Int = checkNotNull(savedStateHandle["taskId"]) {
        "taskId is required for ExecutionViewModel"
    }

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task.asStateFlow()

    private val _steps = MutableStateFlow<List<Step>>(emptyList())
    val steps: StateFlow<List<Step>> = _steps

    init {
        loadTask()
        loadSteps()
    }

    private fun loadTask() {
        viewModelScope.launch {
            _task.value = taskRepository.getTask(taskId)
        }
    }

    private fun loadSteps() {
        viewModelScope.launch {
            _steps.value = taskRepository.getStepsOfTask(taskId)
        }
    }

    fun completeStep(step: Step) {
        _steps.value = _steps.value.filter { it != step }
    }
}