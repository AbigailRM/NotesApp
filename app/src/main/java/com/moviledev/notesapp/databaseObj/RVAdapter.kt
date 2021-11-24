package com.moviledev.notesapp.databaseObj

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moviledev.notesapp.MainActivity
import com.moviledev.notesapp.R
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class RVAdapter(
    val ctx:Context,
    val ClkDelInterface: MainActivity,
    val ClkInterface:MainActivity
) : RecyclerView.Adapter<RVAdapter.ViewHolder>(){

    private val notes = ArrayList<NoteEntity>()

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val note = v.findViewById<TextView>(R.id.tvNote)
        val upDate = v.findViewById<TextView>(R.id.tvUpDate)
        val del = v.findViewById<ImageView>(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.note.text = notes.get(position).nTitle
        holder.upDate.text = "Updated on: "+ notes[position].timeStamp
        holder.del.setOnClickListener{
            ClkDelInterface.onDeleteClicked(notes[position])
        }
        holder.itemView.setOnClickListener{
            ClkInterface.onNoteClicked(notes[position])
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new: List<NoteEntity>) {

        notes.clear()

        notes.addAll(new)

        notifyDataSetChanged()
    }
}

interface ClkDelInterface {

    fun onDeleteClicked(note: NoteEntity)
}


interface ClkInterface {

    fun onNoteClicked(note: NoteEntity)

}