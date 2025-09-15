package com.mrdarip.stepy.ui.screens.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrdarip.stepy.ui.screens.edit.viewmodel.EditViewModel

@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(), onExit: () -> Unit,
) {
    val task by viewModel.task.collectAsState()

    Column {
        TextField(
            enabled = task != null,
            value = task?.name ?: "",
            onValueChange = {
                viewModel.setTaskName(it)
            },
            label = { Text("TaskName") },
            placeholder = { Text("Name") }
        )


        Row {
            Button(onClick = { onExit() }) {
                Text("Exit")
            }

            Button(onClick = { viewModel.saveTask() }) {
                Text("Save")
            }
        }
    }
}