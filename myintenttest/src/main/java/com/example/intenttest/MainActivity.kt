package com.example.intenttest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.example.intenttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var initTime = 0L
    val items = arrayOf<String>("peach", "apple", "orange", "melon", "mango")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.alertdialog.setOnClickListener {
            AlertDialog.Builder(this).run{
                setTitle("test title")
                setIcon(android.R.drawable.ic_dialog_info)
                setMultiChoiceItems(items, booleanArrayOf(false, false, false, true, false),
                object: DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                        Toast.makeText(applicationContext, "${items[p1]} is ${if(p2) "selected" else "unselected"}", Toast.LENGTH_SHORT).show()
                    }
                })
                setPositiveButton("OK", object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        if (p1 == DialogInterface.BUTTON_POSITIVE)
                            Toast.makeText(applicationContext, "OK", Toast.LENGTH_SHORT).show()
                    }
                })
                setNegativeButton("NO", object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        if (p1 == DialogInterface.BUTTON_NEGATIVE)
                            Toast.makeText(applicationContext, "NO", Toast.LENGTH_SHORT).show()
                    }
                })
                show()
            }
        }

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        val intent = Intent(this, IntentActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 19, intent,
        PendingIntent.FLAG_MUTABLE)

        val actionIntent = Intent(this, DetailActivity::class.java)
        val actionPendingIntent = PendingIntent.getActivity(this, 20, actionIntent,
        PendingIntent.FLAG_UPDATE_CURRENT)


        val KEY_TEXT_REPLY = "key_text_reply"
        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run{
            setLabel("답장")
            build()
        }
        val replyIntent = Intent(this,ReplyReceiver::class.java)
        val replyPendingIntent = PendingIntent.getBroadcast(this, 18, replyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = "ohsopp's channel"
            val channelId = "ohsopp ID"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)

            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(this, channelId)

        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder.run{
            setContentTitle("test title")
            val bigPicture = BitmapFactory.decodeResource(resources, R.drawable.github)
            val bigStyle = NotificationCompat.BigPictureStyle().bigPicture(bigPicture)
            setSmallIcon(android.R.drawable.ic_notification_overlay)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setStyle(bigStyle)
            addAction(NotificationCompat.Action.Builder(null, "ACTION", actionPendingIntent).build())
            addAction(NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
            "답장", replyPendingIntent).addRemoteInput(remoteInput).build())
        }

        binding.notification.setOnClickListener {
            manager.notify(11, builder.build())
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(this, "뒤로 가려면 한 번 더 누르세요", Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }
}