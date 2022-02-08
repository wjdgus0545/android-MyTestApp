package com.example.alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.alertdialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    val items = arrayOf<String>("사과", "배", "수박", "딸기", "복숭아")

    val eventHandler = object: DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            if(p1 == DialogInterface.BUTTON_POSITIVE){
                Log.d("ohsopp", "OK Button")
            }
            else if(p1 == DialogInterface.BUTTON_NEGATIVE){
                Log.d("ohsopp", "NO Button")
            }
            else if(p1 == DialogInterface.BUTTON_NEUTRAL){
                Log.d("ohsopp", "MORE Button")
            }
        }
    }

    val itemEventHandler = object:DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            Log.d("ohsopp", "선택한 과일 : ${items[p1]}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        binding.button.setOnClickListener {
            createItemDialog()
        }

        createDialog()



        setContentView(binding.root)
    }

    fun createDialog(){
        AlertDialog.Builder(this).run{
            setTitle("test dialog")
            setIcon(android.R.drawable.ic_dialog_info)
            setMessage("정말 종료하시겠습니까?")
            setPositiveButton("OK", eventHandler)
            setNegativeButton("NO", eventHandler)
            setNeutralButton("MORE", eventHandler)
            show()
        }
    }

    fun createItemDialog(){
        AlertDialog.Builder(this).run{
            setTitle("item dialog")
            setIcon(android.R.drawable.ic_dialog_info)
            setItems(items, itemEventHandler)
            setPositiveButton("OK", eventHandler)
            setNegativeButton("NO", eventHandler)
            setNeutralButton("MORE", eventHandler)
            show()
        }
    }
}