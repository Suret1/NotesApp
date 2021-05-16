package com.suret.todoapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.suret.todoapp.data.model.FolderModel
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFolder(folderModel: FolderModel)

    @Delete
    suspend fun deleteFolder(folderModel: FolderModel)

    @Update
    suspend fun updateFolder(folderModel: FolderModel)

    @Query("SELECT * FROM folders ORDER BY folderId ASC")
    fun getAllFoldersList(): LiveData<List<FolderModel>>

    @Query("UPDATE notes SET folderID = :idFolder, folderName = :nameFolder WHERE id = :id ")
    suspend fun moveToFolder(idFolder: Int, nameFolder: String, id: Int)

    @Query("DELETE FROM notes WHERE folderID = :idFolder ")
    suspend fun deleteNoteWithFolderID(idFolder: Int)

    @Query("UPDATE notes SET folderName =:newFolderName  WHERE folderId = :id")
    suspend fun updateFolderName(newFolderName: String, id: Int)

}