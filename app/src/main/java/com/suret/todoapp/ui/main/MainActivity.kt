package com.suret.todoapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.suret.todoapp.R
import com.suret.todoapp.data.database.NotesDatabase
import com.suret.todoapp.data.repository.NotesRepository
import com.suret.todoapp.data.viewmodel.NotesViewModel
import com.suret.todoapp.data.viewmodel.NotesViewModelFactory
import com.suret.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var notesViewModel: NotesViewModel
    private lateinit var notesViewModelFactory: NotesViewModelFactory
    private lateinit var repository: NotesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = NotesDatabase.getDatabase(this).notesDao()
        repository = NotesRepository(dao)
        notesViewModelFactory = NotesViewModelFactory(repository)
        notesViewModel =
            ViewModelProvider(this, notesViewModelFactory).get(NotesViewModel::class.java)
    }

}