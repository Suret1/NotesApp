package com.suret.todoapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suret.todoapp.data.model.NotesModel
import com.suret.todoapp.data.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    val notes = repository.getAllNotesList

    fun insertNote(note: NotesModel) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun deleteNote(note: NotesModel) = viewModelScope.launch {
        repository.deleteNote(note)
    }


}