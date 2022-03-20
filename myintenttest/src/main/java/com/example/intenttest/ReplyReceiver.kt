package com.example.intenttest

import android.app.NotificationManager
import android.app.RemoteInput
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val replyTxt = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")

        Toast.makeText(context, replyTxt, Toast.LENGTH_SHORT).show()

        val manager = context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

    }
}