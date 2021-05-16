package com.suret.todoapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "folders")
data class FolderModel(
    @PrimaryKey(autoGenerate = true)
    val folderId: Int? = null,
    val title: String
) : Parcelable


