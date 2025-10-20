package com.mrdarip.stepy.ui.screens.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.ui.components.ReorderableStepsList
import com.mrdarip.stepy.ui.screens.edit.viewmodel.EditViewModel

@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(), onExit: () -> Unit,
) {
    val task by viewModel.task.collectAsState()
    val steps by viewModel.steps.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        TextField(
            enabled = task != null,
            value = task?.name ?: "",
            onValueChange = {
                viewModel.setTaskName(it)
            },
            label = { Text("TaskName") },
            placeholder = { Text("Name") }
        )

        Column(Modifier.weight(1f)) {
            Button(
                onClick = {
                    viewModel.setSteps(
                        steps.toMutableList().apply {
                            add(Step(0, "New Step", steps.size, task?.id ?: 0))
                        }
                    )
                },
                enabled = task != null
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Step")
                Text("Add New Step")
            }

            ReorderableStepsList(
                steps
            ) { newSteps ->
                viewModel.setSteps(newSteps)
            }
        }


        Row {
            Button(onClick = { onExit() }) {
                Text("Exit")
            }

            Button(onClick = {
                viewModel.saveTask()
                onExit()
            }) {
                Text("Save")
            }
        }
    }
}