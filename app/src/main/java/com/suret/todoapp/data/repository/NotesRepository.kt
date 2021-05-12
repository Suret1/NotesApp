package com.suret.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.suret.todoapp.data.dao.NotesDao
import com.suret.todoapp.data.model.NotesModel

class NotesRepository(private val notesDao: NotesDao) {

    val getAllNotesList: LiveData<List<NotesModel>> = notesDao.getAllNotesList()

    suspend fun insertNote(note: NotesModel) {
        notesDao.insertNote(note)
    }

    suspend fun deleteNote(note: NotesModel) {
        notesDao.deleteNote(note)
    }

    suspend fun updateNote(note: NotesModel) {
        notesDao.updateNote(note)
    }


}