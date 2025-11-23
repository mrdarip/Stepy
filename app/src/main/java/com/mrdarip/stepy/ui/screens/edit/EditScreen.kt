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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrdarip.stepy.R
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
                steps,
                onOrderChanged = { newSteps ->
                    viewModel.setSteps(newSteps)
                },
                onEdit = { step, newStep ->
                    viewModel.setSteps(
                        steps.toMutableList().apply {
                            set(steps.indexOf(step), newStep)
                        }
                    )

                },
                onDelete = { step ->
                    viewModel.setSteps(
                        steps.toMutableList().apply {
                            //reduce by one all the steps positions after the removed and remove the removed
                            remove(step)
                            for (i in step.position?.until(size)!!) {
                                val currentStep = get(i)
                                set(i, currentStep.copy(position = currentStep.position?.minus(1)))
                            }
                        }
                    )
                }
            )
        }


        Row {
            Button(onClick = { onExit() }) {
                Text(stringResource(R.string.edit_button_exit))
            }

            Button(onClick = {
                viewModel.saveTask()
                onExit()
            }) {
                Text(stringResource(R.string.edit_button_save))
            }
        }
    }
}