package com.example.notepad.database.Write

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R

import kotlinx.android.synthetic.main.item_layout.view.*

class WriteAdapter(var contextA: Context) : RecyclerView.Adapter<WriteAdapter.WriteHolder>() {
    var writes : List<Write> = listOf()

    class WriteHolder(val contextH: Context, itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(write: Write) = with(itemView){
            itemTitle.text = write.title
            itemContent.text = write.content
            itemView.setOnClickListener{
                //при нажатии на элемент открывается окно с редактированием
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteHolder {
        return WriteHolder(contextA, LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: WriteHolder, position: Int) {
        holder.bind(writes[position])
    }

    override fun getItemCount(): Int {
        return writes.size
    }

    fun refreshSubjects(writes : List<Write>){
        this.writes = writes
        notifyDataSetChanged()
    }
}