package com.mrdarip.stepy.ui.screens.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mrdarip.stepy.domain.model.Routine

@Composable
fun RoutineCard(
    routine: Routine,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick
    ) {
        Text(routine.name)
    }
}
