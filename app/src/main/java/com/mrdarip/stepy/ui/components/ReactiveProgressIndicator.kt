package com.mrdarip.stepy.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mrdarip.stepy.domain.model.Execution
import com.mrdarip.stepy.domain.model.StepStats
import kotlinx.coroutines.delay

@Composable
fun ReactiveProgressIndicator(currentExecution: Execution?, stepStats: StepStats?) {
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis() / 1000L) }

    // Tick while the execution is running
    LaunchedEffect(currentExecution) {
        // Only if we have an active execution
        if (currentExecution != null) {
            while (true) {
                currentTime = System.currentTimeMillis() / 1000L // Get only the seconds
                delay(100L)
            }
        }
    }

    LinearProgressIndicator(
        progress = {
            if (currentExecution != null && stepStats != null && stepStats.upperBoundETA > 0) {
                val elapsedTime = currentTime - currentExecution.start

                (elapsedTime.toFloat() / (stepStats.upperBoundETA))
                    .coerceIn(0f, 1f)
            } else {
                0f
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}