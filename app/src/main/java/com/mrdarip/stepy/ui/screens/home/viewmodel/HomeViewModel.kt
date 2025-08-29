package com.mrdarip.stepy.ui.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrdarip.stepy.domain.model.Routine
import com.mrdarip.stepy.domain.model.Task
import com.mrdarip.stepy.domain.repository.RoutineRepository
import com.mrdarip.stepy.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val routineRepository: RoutineRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _routines = MutableStateFlow<List<Routine>>(emptyList())
    val routines: StateFlow<List<Routine>> = _routines

    init {
        loadTasks()
        loadRoutines()
    }

    fun loadRoutines() {
        viewModelScope.launch {
            _routines.value = routineRepository.getRoutines()
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = taskRepository.getTasks()
        }
    }

    fun addTask(name: String) {
        viewModelScope.launch {
            taskRepository.addTask(Task(name = name))
            loadTasks()
        }
    }

    fun addRoutine(name: String) {
        viewModelScope.launch {
            routineRepository.addRoutine(Routine(name = name))
            loadRoutines()
        }
    }
}