package com.suret.todoapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "notes")
@Parcelize
data class NotesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val note: String,
    val date: String
) : Parcelable
