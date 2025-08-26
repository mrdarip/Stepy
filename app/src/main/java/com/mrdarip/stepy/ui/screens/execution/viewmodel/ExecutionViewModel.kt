package com.mrdarip.stepy.ui.screens.execution.viewmodel

import androidx.lifecycle.ViewModel
import com.mrdarip.stepy.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExecutionViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {


}