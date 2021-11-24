package com.moviledev.notesapp.generalFiles.manage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.moviledev.notesapp.MainActivity
import com.moviledev.notesapp.R
import com.moviledev.notesapp.databaseObj.NoteEntity
import com.moviledev.notesapp.databaseObj.ViewModel
import com.moviledev.notesapp.databinding.ActivityCreateNoteBinding
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateNoteBinding

    var id=-1

    @InternalCoroutinesApi
    lateinit var vModel:ViewModel

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vModel=ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModel::class.java)

        //getting data from an intent
        val nType = intent.getStringExtra("nType")
        if (nType.equals("Edit")) {

            val noteTitle = intent.getStringExtra("nTitle")
            val noteDescription = intent.getStringExtra("bodyNote")
            id = intent.getIntExtra("id", -1)
            binding.btnSubmit.text = "Update Note"
            binding.etTitleNote.setText(noteTitle)
            binding.etBodyNote.setText(noteDescription)
        } else {
            binding.btnSubmit.text="Save Note"
        }

        binding.btnSubmit.setOnClickListener {

            val noteTitle = binding.etTitleNote.text.toString()
            val bodyNote = binding.etBodyNote.text.toString()

            if (nType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && bodyNote.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentTime: String = sdf.format(Date())
                    val upNote = NoteEntity(noteTitle, bodyNote, currentTime)
                    upNote.id = id
                    vModel.updateNote(upNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && bodyNote.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentTime: String = sdf.format(Date())

                    vModel.insertNote(NoteEntity(noteTitle, bodyNote, currentTime))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
                if(noteTitle.isEmpty()&&bodyNote.isEmpty()){
                    Toast.makeText(this,"Some field is empty!",Toast.LENGTH_SHORT).show()
                }
            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}