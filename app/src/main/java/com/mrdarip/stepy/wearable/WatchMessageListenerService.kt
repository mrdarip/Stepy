package com.mrdarip.stepy.wearable

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class WatchMessageListenerService : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == COMPLETE_STEP_PATH) {
            val intent = Intent(ACTION_COMPLETE_STEP)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    companion object {
        const val COMPLETE_STEP_PATH = "/complete_step"
        const val ACTION_COMPLETE_STEP = "com.mrdarip.stepy.COMPLETE_STEP"
    }
}
