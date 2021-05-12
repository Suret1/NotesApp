package com.suret.todoapp.ui.fragments.all

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.suret.todoapp.R
import com.suret.todoapp.databinding.FragmentAllBinding
import com.suret.todoapp.ui.main.MainActivity
import com.suret.todoapp.ui.main.adapters.NotesRecyclerAdapter


class AllFragment : Fragment() {
    private lateinit var allBinding: FragmentAllBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        allBinding = FragmentAllBinding.inflate(inflater, container, false)
        return allBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
        val bundle = Bundle()

        val adapter = NotesRecyclerAdapter()

        allBinding.apply {
            notesRecycler.adapter = adapter
            floatingActionButton.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_addNoteBottomSheet)
            }

        }

        (activity as MainActivity).notesViewModel.notes.observe(viewLifecycleOwner) { list ->
            list?.let {
                adapter.differ.submitList(list)
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

}