package com.example.alertdialog

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val replyText = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")

        Log.d("ohsopp", "replyText : $replyText")
        Toast.makeText(context.applicationContext, replyText, Toast.LENGTH_SHORT).show()

        val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager


        manager.cancel(11)

    }
}