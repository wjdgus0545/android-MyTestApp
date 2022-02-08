package com.example.testapp

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val items = arrayOf<String>("사과", "배", "포도", "복숭아", "수박")

    lateinit var binding:ActivityMainBinding

    val eventHandler = object: DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            if(p1 == DialogInterface.BUTTON_POSITIVE){
                Log.d("ohspp", "OK Button")
            }
            else if(p1 == DialogInterface.BUTTON_NEGATIVE){
                Log.d("ohsopp","NO Button")
            }
            else if(p1 == DialogInterface.BUTTON_NEUTRAL){
                Log.d("ohsopp","MORE Button")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)



        binding.button.setOnClickListener {
            createListDialog()
        }

        createDialog()


        setContentView(binding.root)
    }

    fun showToast(str: String){
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    fun createDialog(){
        AlertDialog.Builder(this).run{
            setTitle("test dialog")
            setIcon(android.R.drawable.ic_dialog_info)
            setMessage("test message")
            setPositiveButton("OK", eventHandler)
            setNegativeButton("NO", eventHandler)
            setNeutralButton("MORE", eventHandler)
            show()
        }
    }

    fun createListDialog(){
        AlertDialog.Builder(this).run{
            setTitle("test list dialog")
            setIcon(android.R.drawable.ic_dialog_info)
            setItems(items, object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    showToast("selected fruit : ${items[p1]}")
                }
            })
            setPositiveButton("OK", eventHandler)
            show()
        }
    }

}