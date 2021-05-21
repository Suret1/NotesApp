package com.suret.todoapp.ui.fragments.notesinfolder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.get
import com.suret.todoapp.R
import com.suret.todoapp.data.model.FolderModel
import com.suret.todoapp.databinding.FragmentNotesInFolderBinding
import com.suret.todoapp.ui.main.MainActivity
import com.suret.todoapp.ui.main.adapters.NotesRecyclerAdapter


class NotesInFolderFragment : Fragment() {
    private lateinit var notesInFolder: FragmentNotesInFolderBinding
    private var folderModel: FolderModel? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notesInFolder = FragmentNotesInFolderBinding.inflate(inflater, container, false)
        return notesInFolder.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = Bundle()

        val adapter = NotesRecyclerAdapter()
        navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)

        folderModel = arguments?.getParcelable("folderModel")



        notesInFolder.apply {
            notesInFolderRecycler.adapter = adapter

            addNoteFab.setOnClickListener {
                bundle.apply {
                    putBoolean("fromNote", true)
                    putParcelable("folderModelFromNIF", folderModel)
                }
                navController.navigate(R.id.action_mainFragment_to_addNoteBottomSheet, bundle)
            }
        }

        folderModel?.folderId?.let {
            (activity as MainActivity).notesViewModel.getNotesInFolderList(it)
                .observe(viewLifecycleOwner) { list ->
                    list?.let { notes ->
                        adapter.differ.submitList(notes)
                    }
                }
        }
        adapter.setOnLongItemClickListener {
            it.let { note ->
                bundle.apply {
                    putParcelable("noteModel", note)
                }
                navController.navigate(R.id.action_menuBottomSheet, bundle)
            }
        }
    }

    private fun isDesiredDestination(): Boolean {
        return with(navController) {
            currentDestination == graph[R.id.folderFragment]
        }
    }

}


