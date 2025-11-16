package com.mrdarip.stepy.ui.screens.execution

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    viewModel: ExecutionViewModel = hiltViewModel(), onBackClicked: () -> Unit, onFinish: () -> Unit
) {
    val task by viewModel.task.collectAsState()
    val steps by viewModel.steps.collectAsState()
    val currentExecution by viewModel.currentExecution.collectAsState()

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
                        viewModel.completeExecution(onFinish)
                    }
                },
                enabled = currentExecution != null,
            ) {
                Text(text = "Complete Step")
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (steps.size > 1) {
                Text(text = "Next Steps")
                LazyColumn {
                    items(steps.drop(1)) { step ->
                        Text(text = "- ${step.name}")
                    }
                }
            }

        }
    }
}