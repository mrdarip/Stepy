package com.mrdarip.stepy.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrdarip.stepy.domain.model.Step
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun ReorderableStepsList(
    items: List<Step>,
    onOrderChanged: (List<Step>) -> Unit,
    onEdit: (oldStep: Step, newStep: Step) -> Unit,
    onDelete: (Step) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current

    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        val reorderedItems = items.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }.mapIndexed { index, step -> step.copy(position = index) }

        onOrderChanged(reorderedItems)

        hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items, key = { it.hashCode() }) { step ->
            ReorderableItem(reorderableLazyListState, key = step.hashCode()) { isDragging ->
                var isEditing by remember { mutableStateOf(false) }
                var editedName by remember { mutableStateOf(step.name) }

                val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)

                Surface(
                    shadowElevation = elevation,
                    shape = RoundedCornerShape(12.dp),
                    tonalElevation = if (isDragging) 2.dp else 0.dp,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                modifier = Modifier.draggableHandle(
                                    onDragStarted = {
                                        //hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
                                    },
                                    onDragStopped = {
                                        //hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureEnd)
                                    },
                                ),
                                onClick = {},
                            ) {
                                Icon(Icons.Rounded.Menu, contentDescription = "Reorder")
                            }

                            if (isEditing) {
                                TextField(
                                    value = editedName,
                                    onValueChange = { editedName = it },
                                    singleLine = true,
                                    textStyle = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.widthIn(min = 100.dp, max = 220.dp),
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            onEdit(step, step.copy(name = editedName))
                                            isEditing = false
                                        }) {
                                            Icon(Icons.Rounded.Check, contentDescription = "Save")
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(onDone = {
                                        onEdit(step, step.copy(name = editedName))
                                        isEditing = false
                                    })
                                )
                            } else {
                                Text(
                                    step.name,
                                    Modifier
                                        .padding(horizontal = 8.dp)
                                        .clickable { isEditing = true },
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                        Row {
                            if (!isEditing) {
                                IconButton(onClick = { isEditing = true }) {
                                    Icon(Icons.Rounded.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { onDelete(step) }) {
                                    Icon(Icons.Rounded.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    ReorderableStepsList(
        items = listOf(
            Step(0, "item 1", 0, 0),
            Step(1, "item 2", 1, 0),
            Step(2, "item 3", 2, 0),
            Step(3, "item 4", 3, 0)
        ),
        onOrderChanged = {},
        onEdit = { step, newStep -> },
        onDelete = {}
    )
}