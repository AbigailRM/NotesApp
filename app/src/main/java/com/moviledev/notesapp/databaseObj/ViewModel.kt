package com.moviledev.notesapp.databaseObj

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ViewModel(application: Application):AndroidViewModel(application) {
    val notes:LiveData<List<NoteEntity>>
    val repo:Repository

    init {
        val dao = NotesDB.getDatabase(application).getDAO()
        repo= Repository(dao)
        notes=repo.notes
    }

    fun insertNote(note:NoteEntity)=viewModelScope.launch(Dispatchers.IO) {
        repo.insert(note)
    }

    fun updateNote(note:NoteEntity)=viewModelScope.launch(Dispatchers.IO){
        repo.update(note)
    }

    fun deleteNote(note:NoteEntity)=viewModelScope.launch(Dispatchers.IO){
        repo.delete(note)
    }
}