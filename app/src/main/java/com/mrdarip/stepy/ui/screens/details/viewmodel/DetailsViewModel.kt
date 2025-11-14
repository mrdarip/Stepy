package com.mrdarip.stepy.ui.screens.details.viewmodel

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
class DetailsViewModel @Inject constructor(
    private val taskRepository: TaskRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Load Route params
    private val taskId: Int = checkNotNull(savedStateHandle["taskId"]) {
        "taskId is required for DetailsViewModel"
    }

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task.asStateFlow()


    private val _steps = MutableStateFlow<List<Step>>(emptyList())
    val steps: StateFlow<List<Step>> = _steps.asStateFlow()

    init {
        reloadTask()
    }

    fun reloadTask() {
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
}
