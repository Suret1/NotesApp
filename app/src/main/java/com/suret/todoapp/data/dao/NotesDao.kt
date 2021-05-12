package com.suret.todoapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.suret.todoapp.data.model.NotesModel

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NotesModel)

    @Delete
    suspend fun deleteNote(note: NotesModel)

    @Update
    suspend fun updateNote(note: NotesModel)

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getAllNotesList(): LiveData<List<NotesModel>>

}