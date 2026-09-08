package com.mrdarip.stepy.wearable

import android.content.Context
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable

class ExecutionDataSync(context: Context) {

    private val dataClient: DataClient = Wearable.getDataClient(context)

    fun syncExecutionState(
        taskId: Long,
        taskName: String,
        currentStepName: String,
        currentStepPosition: Int,
        totalSteps: Int,
        executionStart: Long,
        upperBoundEta: Long
    ) {
        val request = PutDataMapRequest.create(EXECUTION_PATH).apply {
            dataMap.putBoolean(KEY_IS_RUNNING, true)
            dataMap.putLong(KEY_TASK_ID, taskId)
            dataMap.putString(KEY_TASK_NAME, taskName)
            dataMap.putString(KEY_CURRENT_STEP_NAME, currentStepName)
            dataMap.putInt(KEY_CURRENT_STEP_POSITION, currentStepPosition)
            dataMap.putInt(KEY_TOTAL_STEPS, totalSteps)
            dataMap.putLong(KEY_EXECUTION_START, executionStart)
            dataMap.putLong(KEY_UPPER_BOUND_ETA, upperBoundEta)
        }

        val putRequest = request.asPutDataRequest().setUrgent()
        dataClient.putDataItem(putRequest)
    }

    fun clearExecution() {
        val request = PutDataMapRequest.create(EXECUTION_PATH).apply {
            dataMap.putBoolean(KEY_IS_RUNNING, false)
        }

        val putRequest = request.asPutDataRequest().setUrgent()
        dataClient.putDataItem(putRequest)
    }

    companion object {
        const val EXECUTION_PATH = "/execution"

        const val KEY_IS_RUNNING = "is_running"
        const val KEY_TASK_ID = "task_id"
        const val KEY_TASK_NAME = "task_name"
        const val KEY_CURRENT_STEP_NAME = "current_step_name"
        const val KEY_CURRENT_STEP_POSITION = "current_step_position"
        const val KEY_TOTAL_STEPS = "total_steps"
        const val KEY_EXECUTION_START = "execution_start"
        const val KEY_UPPER_BOUND_ETA = "upper_bound_eta"
    }
}
