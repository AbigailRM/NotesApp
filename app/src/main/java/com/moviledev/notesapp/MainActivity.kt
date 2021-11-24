package com.moviledev.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.moviledev.notesapp.databaseObj.*
import com.moviledev.notesapp.databinding.ActivityMainBinding
import com.moviledev.notesapp.generalFiles.manage.CreateNoteActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MainActivity : AppCompatActivity(),ClkInterface,ClkDelInterface {

    lateinit var nRV:RecyclerView
    lateinit var floatBtn:FloatingActionButton
    lateinit var vModel:ViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nRV=binding.notesRv
        floatBtn=binding.floatButton

        nRV.layoutManager=LinearLayoutManager(this)

        val rvAdapter=RVAdapter(this,this,this)
        nRV.adapter=rvAdapter

        vModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(ViewModel::class.java)

        this.vModel.notes.observe(this, Observer { list->list?.let{
            rvAdapter.updateList(it)
        } })

        floatBtn.setOnClickListener{
            val intent=Intent(this@MainActivity,CreateNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    override fun onDeleteClicked(note: NoteEntity) {
        vModel.deleteNote(note)
        Toast.makeText(this,"${note.nTitle} was deleted successfully",Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClicked(note: NoteEntity) {
        val intent=Intent(this@MainActivity,CreateNoteActivity::class.java)

        intent.putExtra("NoteType","Edit")
        intent.putExtra("nTitle",note.nTitle)
        intent.putExtra("BodyNote",note.nBody)
        intent.putExtra("ID",note.id)

        startActivity(intent)
        this.finish()
    }

}