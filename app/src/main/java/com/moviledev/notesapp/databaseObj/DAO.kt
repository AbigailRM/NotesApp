package com.moviledev.notesapp.databaseObj


import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao interface DAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : NoteEntity)


    @Delete
    suspend fun delete(note: NoteEntity)


    @Query("Select * from tblNotes order by id ASC")
    fun getAllNotes(): LiveData<List<NoteEntity>>


    @Update
    suspend fun update(note: NoteEntity)
}