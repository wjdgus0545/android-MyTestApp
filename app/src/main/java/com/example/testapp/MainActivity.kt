package com.example.testapp

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val name = TextView(this).apply{
            typeface = Typeface.DEFAULT_BOLD
            text = "Lake Louise"
        }

        val image = ImageView(this).also{
            it.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground))
        }

        val address = TextView(this).apply{
            typeface = Typeface.DEFAULT_BOLD
            text = "Lake Louise, AB, Canada"
        }
        val layout = LinearLayout(this).apply{
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER

            addView(name, WRAP_CONTENT, WRAP_CONTENT)
            addView(image, WRAP_CONTENT, WRAP_CONTENT)
            addView(address, WRAP_CONTENT, WRAP_CONTENT)
        }

        binding.btn1.setOnClickListener {
            val text1 = binding.editid.text.toString()
            val text2 = binding.textid.text.toString()
            Toast.makeText(applicationContext, text1+" " +text2, Toast.LENGTH_SHORT).show()
        }

        setContentView(binding.root)
    }
}