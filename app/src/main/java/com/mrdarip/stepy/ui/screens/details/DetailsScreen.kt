package com.mrdarip.stepy.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.mrdarip.stepy.R
import com.mrdarip.stepy.domain.model.Task
import com.mrdarip.stepy.ui.components.BackButton
import com.mrdarip.stepy.ui.screens.details.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onExecuteTaskClick: (Task) -> Unit,
    onEditTaskClick: (Task) -> Unit
) {
    LifecycleResumeEffect(Unit) {
        viewModel.reloadTask()
        onPauseOrDispose { }
    }

    val task by viewModel.task.collectAsState()
    val steps by viewModel.steps.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton(onBackClicked)
            IconButton(enabled = task != null, onClick = { task?.let { onEditTaskClick(it) } }) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "Edit Task")
            }
        }

        task?.let { t ->
            Column() {
                Text(text = "name = ${t.name}")
                Button(onClick = { onExecuteTaskClick(t) }) {
                    Text(stringResource(R.string.details_button_execute))
                }
                LazyColumn {
                    itemsIndexed(steps) { index, step ->
                        Text(text = "${index + 1}. ${step.name}")
                    }

                }

            }


        }

    }
}