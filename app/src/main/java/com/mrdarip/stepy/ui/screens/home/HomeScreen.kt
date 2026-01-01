package com.mrdarip.stepy.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.mrdarip.stepy.R
import com.mrdarip.stepy.domain.model.Task
import com.mrdarip.stepy.ui.screens.home.viewmodel.HomeViewModel
import com.mrdarip.stepy.ui.theme.StepyTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(), onExecuteTaskClick: (Task) -> Unit
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadTasks()
        onPauseOrDispose { }
    }

    val tasks by viewModel.tasks.collectAsState()
    val routines by viewModel.routines.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Routines",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )

            IconButton(onClick = { viewModel.addRoutine("New Routine") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Routine")
            }
        }

        LazyRow {
            items(routines) { routine ->
                RoutineCard(routine) {
                    //TODO: Navigate to routine execution screen
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Tasks",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )

            IconButton(onClick = { viewModel.addTask("New Task") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }

        LazyColumn {
            items(tasks) { task ->
                TaskCard(task, { onExecuteTaskClick(task) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    StepyTheme {
        HomeScreen(
            onExecuteTaskClick = { task ->
                println("Task clicked: ${task.name}")
            }
        )
    }
}
