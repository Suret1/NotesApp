package com.suret.todoapp.ui.fragments.folder

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.get
import com.google.android.material.snackbar.Snackbar
import com.suret.todoapp.R
import com.suret.todoapp.data.model.FolderModel
import com.suret.todoapp.data.model.NotesModel
import com.suret.todoapp.databinding.FragmentFolderBinding
import com.suret.todoapp.ui.main.MainActivity
import com.suret.todoapp.ui.main.adapters.FolderRecyclerAdapter

class FolderFragment : Fragment() {
    private lateinit var folderBinding: FragmentFolderBinding
    private var alertDialog: AlertDialog? = null
    private var note: NotesModel? = null
    private val bundle = Bundle()
    private lateinit var navController: NavController
    private var runnable: Runnable = Runnable { }
    private var handler: Handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        folderBinding = FragmentFolderBinding.inflate(inflater, container, false)
        return folderBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)

        val folderAdapter = FolderRecyclerAdapter()
        note = arguments?.getParcelable("noteModel")

        folderBinding.apply {
            folderRecycler.adapter = folderAdapter
            addFolderFab.setOnClickListener {
                showDialog()
            }
        }
        (activity as MainActivity).notesViewModel.folders.observe(viewLifecycleOwner) { list ->
            list?.let {
                folderAdapter.differ.submitList(list)
            }
        }
        folderAdapter.setOnLongItemClickListener {
            it.let { folder ->
                bundle.apply {
                    putParcelable("folderModel", folder)
                }
                navController.navigate(R.id.action_menuBottomSheet, bundle)
            }
        }
        folderAdapter.setOnItemClickListener { folder ->

            bundle.apply {
                if (note != null) {
                    folder.folderId?.let { idFolder ->
                        note?.id?.let { idNote ->
                            (activity as MainActivity).notesViewModel.moveToFolder(
                                idFolder,
                                folder.title,
                                idNote
                            )
                            runnable = Runnable {
                                activity?.onBackPressed()
                                handler.postDelayed(runnable, 2000)
                            }
                            Snackbar.make(
                                requireView(),
                                getString(R.string.moved_success),
                                800
                            ).show()
                            handler.post(runnable)
                        }
                    }

                } else {
                    bundle.apply {
                        putParcelable("folderModel", folder)
                    }
                    navController.navigate(
                        com.suret.todoapp.R.id.action_to_notesInFolderFragment,
                        bundle
                    )
                }
            }

        }
    }

    private fun showDialog() {

        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.folder_save_dialog, null)
        val folderET = view.findViewById<AppCompatEditText>(R.id.folderName)
        val saveBtn = view.findViewById<AppCompatButton>(R.id.save_btn)
        val cancelBtn = view.findViewById<AppCompatButton>(R.id.cancel_btn)

        builder.apply {
            setView(view)
            setCancelable(false)
            alertDialog = create()
            alertDialog?.show()
        }

        saveBtn.setOnClickListener {
            val folderName = folderET.text.toString().trim()
            insertFolder(folderName)
        }

        cancelBtn.setOnClickListener {
            alertDialog?.dismiss()
        }

    }

    private fun insertFolder(foldername: String) {
        if (foldername.isNotEmpty()) {
            val folder = FolderModel(null, foldername)
            (activity as MainActivity).notesViewModel.insertFolder(folder)
            Snackbar.make(
                requireView(),
                getString(R.string.saved_success),
                800
            ).show()
            alertDialog?.dismiss()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_folder_name),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}