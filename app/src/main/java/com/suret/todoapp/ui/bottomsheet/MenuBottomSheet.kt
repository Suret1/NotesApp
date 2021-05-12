package com.suret.todoapp.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.suret.todoapp.R
import com.suret.todoapp.data.model.NotesModel
import com.suret.todoapp.databinding.MenuBottomSheetBinding
import com.suret.todoapp.ui.main.MainActivity

class MenuBottomSheet : BottomSheetDialogFragment() {
    private lateinit var menuBinding: MenuBottomSheetBinding
    private var note: NotesModel? = null
    private lateinit var navController: NavController
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
        navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
        val bundle = Bundle()
        note = arguments?.getParcelable("noteModel")

        menuBinding.apply {
            deleteBtn.setOnClickListener {
                deleteNote()
            }
            editBtn.setOnClickListener {
                bundle.apply {
                    putParcelable("noteModel", note)
                    putBoolean("editCheck", true)
                }
                activity?.onBackPressed()
                navController.navigate(R.id.action_mainFragment_to_addNoteBottomSheet, bundle)

            }
        }

    }

    private fun deleteNote() {
        note?.let { it1 -> (activity as MainActivity).notesViewModel.deleteNote(it1) }
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.delete_successfully),
            Toast.LENGTH_SHORT
        ).show()
        activity?.onBackPressed()
    }


}