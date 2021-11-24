package com.moviledev.notesapp.databaseObj

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import java.util.concurrent.Flow

class Repository(private val dao:DAO) {
    val notes:LiveData<List<NoteEntity>> = dao.getAllNotes()

    suspend fun insert(note:NoteEntity){
        dao.insert(note)
    }

    suspend fun update(note:NoteEntity){
        dao.update(note)
    }

    suspend fun delete(note: NoteEntity){
        dao.delete(note)
    }

}