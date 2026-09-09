package com.mrdarip.stepy.data

import android.content.Context
import android.net.Uri
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class WearExecutionRepository(context: Context) {

    private val appContext = context.applicationContext
    private val dataClient: DataClient = Wearable.getDataClient(appContext)
    private val messageClient: MessageClient = Wearable.getMessageClient(appContext)
    private val nodeClient = Wearable.getNodeClient(appContext)

    fun observeExecutionState(): Flow<WearExecutionState?> = callbackFlow {
        val listener = DataClient.OnDataChangedListener { events: DataEventBuffer ->
            val state = parseExecutionState(events)
            trySend(state)
        }

        dataClient.addListener(listener, EXECUTION_URI, DataClient.FILTER_LITERAL)

        awaitClose {
            dataClient.removeListener(listener)
        }
    }

    suspend fun getExecutionState(): WearExecutionState? {
        val dataItems = dataClient.getDataItems(EXECUTION_URI).await()
        var state: WearExecutionState? = null
        for (item in dataItems) {
            if (item.uri.path == EXECUTION_PATH) {
                val dataMap = DataMapItem.fromDataItem(item).dataMap
                val isRunning = dataMap.getBoolean(KEY_IS_RUNNING, false)
                if (isRunning) {
                    state = WearExecutionState(
                        taskId = dataMap.getLong(KEY_TASK_ID, 0),
                        taskName = dataMap.getString(KEY_TASK_NAME, ""),
                        currentStepName = dataMap.getString(KEY_CURRENT_STEP_NAME, ""),
                        currentStepPosition = dataMap.getInt(KEY_CURRENT_STEP_POSITION, 0),
                        totalSteps = dataMap.getInt(KEY_TOTAL_STEPS, 0),
                        executionStart = dataMap.getLong(KEY_EXECUTION_START, 0),
                        upperBoundEta = dataMap.getLong(KEY_UPPER_BOUND_ETA, 0)
                    )
                }
            }
        }
        dataItems.release()
        return state
    }

    suspend fun sendCompleteStepMessage() {
        val nodes = nodeClient.connectedNodes.await()
        for (node in nodes) {
            messageClient.sendMessage(node.id, COMPLETE_STEP_PATH, null).await()
        }
    }

    private fun parseExecutionState(events: DataEventBuffer): WearExecutionState? {
        for (event in events) {
            if (event.type == DataEvent.TYPE_CHANGED &&
                event.dataItem.uri.path == EXECUTION_PATH
            ) {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val isRunning = dataMap.getBoolean(KEY_IS_RUNNING, false)
                if (!isRunning) return null

                return WearExecutionState(
                    taskId = dataMap.getLong(KEY_TASK_ID, 0),
                    taskName = dataMap.getString(KEY_TASK_NAME, ""),
                    currentStepName = dataMap.getString(KEY_CURRENT_STEP_NAME, ""),
                    currentStepPosition = dataMap.getInt(KEY_CURRENT_STEP_POSITION, 0),
                    totalSteps = dataMap.getInt(KEY_TOTAL_STEPS, 0),
                    executionStart = dataMap.getLong(KEY_EXECUTION_START, 0),
                    upperBoundEta = dataMap.getLong(KEY_UPPER_BOUND_ETA, 0)
                )
            }
        }
        return null
    }

    companion object {
        private const val EXECUTION_PATH = "/execution"
        private val EXECUTION_URI = Uri.Builder()
            .scheme("wear")
            .authority("*")
            .path(EXECUTION_PATH)
            .build()

        private const val COMPLETE_STEP_PATH = "/complete_step"

        private const val KEY_IS_RUNNING = "is_running"
        private const val KEY_TASK_ID = "task_id"
        private const val KEY_TASK_NAME = "task_name"
        private const val KEY_CURRENT_STEP_NAME = "current_step_name"
        private const val KEY_CURRENT_STEP_POSITION = "current_step_position"
        private const val KEY_TOTAL_STEPS = "total_steps"
        private const val KEY_EXECUTION_START = "execution_start"
        private const val KEY_UPPER_BOUND_ETA = "upper_bound_eta"
    }
}
