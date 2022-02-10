package com.example.alertdialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.alertdialog.databinding.ActivityMainBinding
import com.example.alertdialog.databinding.DialogInputBinding

class MainActivity : AppCompatActivity() {

    var initTime = 0L

    lateinit var binding:ActivityMainBinding

    val items = arrayOf<String>("apple", "banana", "peach", "lemon", "orange")

    val eventHandler = object: DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            if(p1 == DialogInterface.BUTTON_POSITIVE){
                Log.d("ohsopp", "OK Button")
            }
            else if(p1 == DialogInterface.BUTTON_NEGATIVE){
                Log.d("ohsopp", "NO Button")
            }
            else if(p1 == DialogInterface.BUTTON_NEUTRAL){
                Log.d("ohspop", "MORE Button")
            }
        }
    }

    val itemEventHandler = object: DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            Log.d("ohsopp", "선택한 과일 : ${items[p1]}")
        }
    }

    val multiEventHandler = object: DialogInterface.OnMultiChoiceClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
            Log.d("ohsopp", "${items[p1]}가(이) ${if(p2) "선택되었습니다." else "선택 해제되었습니다."}")
        }
    }

    val singleEventHandler = object: DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            Log.d("ohsopp", "${items[p1]}가 선택되었습니다.")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        binding.button.setOnClickListener {
            createDialog()
        }

        binding.button2.setOnClickListener {
            createItemDialog()
        }

        binding.button3.setOnClickListener {
            createCheckBox()
        }

        binding.button4.setOnClickListener {
            createRadioButton()
        }

        binding.button5.setOnClickListener {
            createCustomDialog()
        }
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(applicationContext, notification)

        binding.button6.setOnClickListener {

            ringtone.play()

        }

        val player: MediaPlayer = MediaPlayer.create(this, R.raw.wineglassshatter)
        binding.button7.setOnClickListener {
            player.start()
        }

        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        binding.button8.setOnClickListener {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        }

        setContentView(binding.root)
    }

    fun createDialog(){
        AlertDialog.Builder(this).run{
            setTitle("test dialog")
            setIcon(android.R.drawable.ic_dialog_info)
            setMessage("test message")
            setPositiveButton("YES", eventHandler)
            setNegativeButton("NO", eventHandler)
            setNeutralButton("MORE", eventHandler)
            show()
        }
    }

    fun createItemDialog(){
        AlertDialog.Builder(this).run {
            setTitle("items test")
            setIcon(android.R.drawable.ic_dialog_info)
            setItems(items, object: DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    Log.d("ohsopp", "Selected fruit : ${items[p1]}")
                }
            })
            setPositiveButton("OK", null)
            show()
        }
    }

    fun createCheckBox(){
        AlertDialog.Builder(this).run{
            setTitle("checkbox dialog")
            setIcon(android.R.drawable.ic_dialog_info)
            setMultiChoiceItems(items, booleanArrayOf(true, false, true, true, false), object: DialogInterface.OnMultiChoiceClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                    Log.d("ohsopp", "${items[p1]} is ${if(p2) "selected" else "released"}")
                }
            })
            setPositiveButton("OK", eventHandler)

            show()
        }
    }

    fun createRadioButton(){
        AlertDialog.Builder(this).run{
            setTitle("radio dialog")
            setIcon(android.R.drawable.ic_dialog_info)
            setSingleChoiceItems(items, 2, object: DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    Log.d("ohsopp", "${items[p1]} is selected")
                }
            })
            setPositiveButton("OK", eventHandler)
            setNegativeButton("NO", eventHandler)
            setNeutralButton("MORE", eventHandler)
            show()
        }
    }

    fun createCustomDialog(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView = inflater.inflate(R.layout.dialog_input, null)
        val dialogBinding = DialogInputBinding.inflate(layoutInflater)

        AlertDialog.Builder(this).run{
            setTitle("Custom Dialog")
            setView(rootView)
            setPositiveButton("닫기", null)
            show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(applicationContext, "종료하려면 한 번 더 누르세요", Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}