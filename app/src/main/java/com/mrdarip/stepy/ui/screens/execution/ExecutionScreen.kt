package com.mrdarip.stepy.ui.screens.execution

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrdarip.stepy.domain.model.Task
import com.mrdarip.stepy.ui.components.BackButton
import com.mrdarip.stepy.ui.screens.execution.viewmodel.ExecutionViewModel

@Composable
fun ExecutionScreen(
    viewModel: ExecutionViewModel = hiltViewModel(), onBackClicked: () -> Unit, taskToExecute: Task
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackButton(onBackClicked)

        Column {
            Text(text = "Task to execute: ${taskToExecute.name}")
        }
    }
}