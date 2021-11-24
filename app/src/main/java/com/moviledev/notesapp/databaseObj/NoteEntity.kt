package com.moviledev.notesapp.databaseObj

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tblNotes")
class NoteEntity (

    @ColumnInfo(name="title") val nTitle:String,

    @ColumnInfo(name="bodyNote") val nBody:String,

    @ColumnInfo(name="timestamp") val timeStamp:String)

{
    @PrimaryKey(autoGenerate = true)
    var id=0
}