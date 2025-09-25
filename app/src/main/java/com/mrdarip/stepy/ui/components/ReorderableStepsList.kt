package com.mrdarip.stepy.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrdarip.stepy.domain.model.Step
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun ReorderableStepsList(
    items: List<Step>,
    onOrderChanged: (List<Step>) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current

    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        onOrderChanged(items.toMutableList().apply {
            add(to.index, removeAt(from.index))
        })

        hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items, key = { it.hashCode() }) {
            ReorderableItem(reorderableLazyListState, key = it.hashCode()) { isDragging ->
                val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)

                Surface(shadowElevation = elevation) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(it.name, Modifier.padding(horizontal = 8.dp))
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
        )
    ) { }
}