package com.mrdarip.stepy.ui.screens.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mrdarip.stepy.domain.model.Task

@Composable
fun TaskCard(
    task: Task,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick
    ) {
        Text(task.name)
    }
}
