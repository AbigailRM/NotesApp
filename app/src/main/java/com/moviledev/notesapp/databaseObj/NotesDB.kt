package com.moviledev.notesapp.databaseObj

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [NoteEntity::class],version = 1,exportSchema = false)

abstract class NotesDB:RoomDatabase() {

    abstract fun getDAO():DAO

    companion object {

        @Volatile
        private var INSTANCE: NotesDB? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context): NotesDB {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDB::class.java,
                    "note_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}