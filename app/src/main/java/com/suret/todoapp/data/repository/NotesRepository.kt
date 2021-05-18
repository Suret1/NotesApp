package com.suret.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.suret.todoapp.data.dao.NotesDao
import com.suret.todoapp.data.model.FolderModel
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

    val getAllFoldersList: LiveData<List<FolderModel>> = notesDao.getAllFoldersList()


    suspend fun insertFolder(folderModel: FolderModel) {
        notesDao.insertFolder(folderModel)
    }

    suspend fun deleteFolder(folderModel: FolderModel) {
        notesDao.deleteFolder(folderModel)
    }

    suspend fun updateFolder(folderModel: FolderModel) {
        notesDao.updateFolder(folderModel)
    }

    suspend fun moveToFolder(idFolder: Int, nameFolder: String, id: Int) {
        notesDao.moveToFolder(idFolder, nameFolder, id)
    }

    suspend fun deleteNoteWithFolderID(idFolder: Int) {
        notesDao.deleteNoteWithFolderID(idFolder)
    }

    suspend fun updateFolderName(newFolderName: String, id: Int) {
        notesDao.updateFolderName(newFolderName, id)
    }

    fun getNotesInFolderList(folderId: Int) = notesDao.getNotesInFolderList(folderId)


}