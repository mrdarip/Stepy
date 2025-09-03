package com.mrdarip.stepy.ui.screens.execution

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrdarip.stepy.ui.components.BackButton
import com.mrdarip.stepy.ui.screens.execution.viewmodel.ExecutionViewModel

@Composable
fun ExecutionScreen(
    viewModel: ExecutionViewModel = hiltViewModel(), onBackClicked: () -> Unit
) {
    val task by viewModel.task.collectAsState()
    val steps by viewModel.steps.collectAsState()

    val currentStep = steps.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackButton(onBackClicked)

        Column {
            Text(text = "Task to execute: ${task?.name}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Current Step: ${currentStep?.name}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    currentStep?.let {
                        viewModel.completeStep(currentStep)
                    }
                },
                enabled = currentStep != null
            ) {
                Text(text = "Complete Step")
            }
        }
    }
}