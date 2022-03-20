package com.example.alertdialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alertdialog.databinding.FragmentOneBinding
import com.example.alertdialog.databinding.ItemMainBinding

class Profiles(val img: Int, val txt: String)

class MyViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<Profiles>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),
        parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding

        binding.itemImage.setImageResource(datas[position].img)
        binding.itemText.text = datas[position].txt

        binding.itemRoot.setOnClickListener {
            Log.d("ohsopp", "selected : ${datas[position].txt}")
        }
    }
}

class MyDecoration(val context: Context): RecyclerView.ItemDecoration(){


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val index = parent.getChildAdapterPosition(view) + 1

        if (index % 3 == 0)
            outRect.set(10, 10, 10, 60)
        else
            outRect.set(10, 10, 10, 10)

        //view.setBackgroundResource(R.drawable.three_fragment_style)
        ViewCompat.setElevation(view, 20.0f)
    }
}

class OneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        val datas = mutableListOf<Profiles>()

        binding.recyclerView.layoutManager = LinearLayoutManager(inflater.context)
        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(MyDecoration(inflater.context))

        binding.lionImage.setOnClickListener {
            datas.add(0, Profiles(R.drawable.lion1, "lion"))
            adapter.notifyDataSetChanged()
        }

        binding.personImage.setOnClickListener {
            datas.add(0, Profiles(R.drawable.person1, "person"))
            adapter.notifyDataSetChanged()
        }

        val eventHandler = object: DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1 == DialogInterface.BUTTON_POSITIVE){
                    datas.clear()
                    adapter.notifyDataSetChanged()
                }
                else if(p1 == DialogInterface.BUTTON_NEGATIVE){

                }
            }
        }
        binding.clear.setOnClickListener {

            AlertDialog.Builder(inflater.context).run{
                setTitle("notification")
                setMessage("정말 삭제하시겠습니까?")
                setIcon(android.R.drawable.ic_dialog_info)
                setPositiveButton("네", eventHandler)
                setNegativeButton("아니오", eventHandler)
                show()
            }
        }
        return binding.root
    }
}