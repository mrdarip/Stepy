package com.mrdarip.stepy.ui.screens.execution

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrdarip.stepy.R
import com.mrdarip.stepy.domain.model.Execution
import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.model.Task
import com.mrdarip.stepy.ui.components.BackButton
import com.mrdarip.stepy.ui.screens.execution.viewmodel.ExecutionViewModel

@Composable
fun ExecutionScreen(
    viewModel: ExecutionViewModel = hiltViewModel(), onBackClicked: () -> Unit, onFinish: () -> Unit
) {

    val task by viewModel.task.collectAsState()
    val steps by viewModel.steps.collectAsState()
    val currentExecution by viewModel.currentExecution.collectAsState()

    ExecutionScreenBodyContent(
        task,
        steps,
        currentExecution,
        onBackClicked,
        { viewModel.completeExecution(onFinish) }
    )
}

@Composable
fun ExecutionScreenBodyContent(
    task: Task?,
    steps: List<Step>,
    currentExecution: Execution?,
    onBackClicked: () -> Unit,
    onStepCompletion: () -> Unit
) {
    val currentStep = steps.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            BackButton(onBackClicked)

            Text(
                text = "${task?.name}",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = currentStep?.name ?: "...", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    currentStep?.let {
                        onStepCompletion()
                    }
                },
                enabled = currentExecution != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.execution_complete_step))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (steps.size > 1) {
                Text(text = stringResource(R.string.execution_title_next))
                LazyColumn {
                    items(steps.drop(1)) { step ->
                        Text(text = "- ${step.name}")
                    }
                }
            }

        }
    }
}