package com.suret.todoapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suret.todoapp.data.model.FolderModel
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

    fun updateNote(note: NotesModel) = viewModelScope.launch {
        repository.updateNote(note)
    }

    val folders = repository.getAllFoldersList

    fun insertFolder(folder: FolderModel) = viewModelScope.launch {
        repository.insertFolder(folder)
    }

    fun deleteFolder(folder: FolderModel) = viewModelScope.launch {
        repository.deleteFolder(folder)
    }

    fun updateFolder(folder: FolderModel) = viewModelScope.launch {
        repository.updateFolder(folder)
    }

    fun moveToFolder(idFolder: Int, nameFolder: String, id: Int) {
        viewModelScope.launch {
            repository.moveToFolder(idFolder, nameFolder, id)
        }
    }

    fun deleteNoteWithFolderID(idFolder: Int) {
        viewModelScope.launch {
            repository.deleteNoteWithFolderID(idFolder)
        }
    }

    fun updateFolderName(newFolderName: String, id: Int) {
        viewModelScope.launch {
            repository.updateFolderName(newFolderName, id)
        }
    }
    fun getNotesInFolderList(folderId: Int): LiveData<List<NotesModel>> =
        repository.getNotesInFolderList(folderId)


}