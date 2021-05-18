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
import com.suret.todoapp.data.model.FolderModel
import com.suret.todoapp.data.model.NotesModel
import com.suret.todoapp.databinding.MenuBottomSheetBinding
import com.suret.todoapp.ui.main.MainActivity

class MenuBottomSheet : BottomSheetDialogFragment() {
    private lateinit var menuBinding: MenuBottomSheetBinding
    private var note: NotesModel? = null
    private var folder: FolderModel? = null
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
        folder = arguments?.getParcelable("folderModel")

        menuBinding.apply {
            if (note != null) {
                deleteBtn.setOnClickListener {
                    deleteNote()
                }
                editBtn.setOnClickListener {
                    bundle.apply {
                        putParcelable("noteModel", note)
                    }
                    activity?.onBackPressed()
                    navController.navigate(R.id.action_mainFragment_to_addNoteBottomSheet, bundle)
                }
                moveBtn.setOnClickListener {
                    bundle.apply {
                        putParcelable("noteModel", note)
                    }
                    navController.navigate(R.id.action_to_folderFragment, bundle)
                }
            } else if (folder != null) {
                moveBtn.visibility = View.GONE
                deleteBtn.setOnClickListener {
                    deleteFolder()
                }
                editBtn.setOnClickListener {
                    bundle.apply {
                        putParcelable("folderModel", folder)
                    }
                    activity?.onBackPressed()
                    navController.navigate(R.id.action_mainFragment_to_addNoteBottomSheet, bundle)
                }
            }
        }

    }

    private fun deleteNote() {
        note?.let { it -> (activity as MainActivity).notesViewModel.deleteNote(it) }
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.delete_success),
            Toast.LENGTH_SHORT
        ).show()
        activity?.onBackPressed()
    }

    private fun deleteFolder() {
        folder?.let { it ->
            it.folderId?.let { it1 ->
                (activity as MainActivity).notesViewModel.deleteNoteWithFolderID(
                    it1
                )
            }
        }
        folder?.let { it ->
            (activity as MainActivity).notesViewModel.deleteFolder(it)
        }
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.delete_success),
            Toast.LENGTH_LONG
        ).show()
        activity?.onBackPressed()
    }

}