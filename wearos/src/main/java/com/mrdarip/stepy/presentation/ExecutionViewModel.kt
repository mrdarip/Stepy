package com.mrdarip.stepy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrdarip.stepy.data.WearExecutionRepository
import com.mrdarip.stepy.data.WearExecutionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExecutionViewModel @Inject constructor(
    private val repository: WearExecutionRepository
) : ViewModel() {

    private val _executionState = MutableStateFlow<WearExecutionState?>(null)
    val executionState: StateFlow<WearExecutionState?> = _executionState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isCompleting = MutableStateFlow(false)
    val isCompleting: StateFlow<Boolean> = _isCompleting.asStateFlow()

    init {
        loadCurrentState()
        observeChanges()
    }

    private fun loadCurrentState() {
        viewModelScope.launch {
            _executionState.value = repository.getExecutionState()
            _isLoading.value = false
        }
    }

    private fun observeChanges() {
        viewModelScope.launch {
            repository.observeExecutionState().collect { state ->
                _executionState.value = state
            }
        }
    }

    fun completeStep() {
        val state = _executionState.value ?: return
        if (_isCompleting.value) return

        viewModelScope.launch {
            _isCompleting.value = true
            try {
                repository.sendCompleteStepMessage()
            } finally {
                _isCompleting.value = false
            }
        }
    }
}
