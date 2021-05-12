package com.suret.todoapp.ui.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.suret.todoapp.R
import com.suret.todoapp.data.model.NotesModel
import com.suret.todoapp.databinding.MenuBottomSheetBinding
import com.suret.todoapp.ui.main.MainActivity

class MenuBottomSheet : BottomSheetDialogFragment() {
    private lateinit var menuBinding: MenuBottomSheetBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        menuBinding = MenuBottomSheetBinding.inflate(inflater, container, false)

        return menuBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val noteID = arguments?.getInt("noteId")
        Log.d("btn", noteID.toString())

        menuBinding.apply {
            deleteBtn.setOnClickListener {
                val note = arguments?.getParcelable<NotesModel>("noteModel")
                note?.let { it1 -> (activity as MainActivity).notesViewModel.deleteNote(it1) }
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.delete_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                activity?.onBackPressed()

            }
        }

    }

}