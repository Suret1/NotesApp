package com.suret.todoapp.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.suret.todoapp.R
import com.suret.todoapp.data.model.FolderModel
import com.suret.todoapp.data.model.NotesModel
import com.suret.todoapp.databinding.AddNoteBottomSheetBinding
import com.suret.todoapp.ui.main.MainActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class AddNoteBottomSheet : BottomSheetDialogFragment() {
    private lateinit var addNoteBottomSheetBinding: AddNoteBottomSheetBinding
    private var noteModel: NotesModel? = null
    private var folderModel: FolderModel? = null
    private var title: String? = null
    private var note: String? = null
    private var date: String? = null
    private val currentDate = LocalDateTime.now()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addNoteBottomSheetBinding = AddNoteBottomSheetBinding.inflate(inflater, container, false)
        return addNoteBottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val offsetFromTop = 200
//        (dialog as? BottomSheetDialog)?.behavior?.apply {
//            isFitToContents = false
//            expandedOffset = offsetFromTop
//            state = BottomSheetBehavior.STATE_EXPANDED
//        }
        date = currentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        noteModel = arguments?.getParcelable("noteModel")
        folderModel = arguments?.getParcelable("folderModel")
        val fromNote = arguments?.getBoolean("fromNote")

        addNoteBottomSheetBinding.apply {
            when {
                fromNote == true -> {
                    saveBtn.setOnClickListener {
                        insertNote()
                    }
                }
                noteModel != null -> {
                    updateUI(noteModel!!)
                    saveBtn.setOnClickListener {
                        updateNote()
                    }
                }
                folderModel != null -> {
                    updateUI(folderModel!!)
                    saveBtn.setOnClickListener {
                        updateFolder()
                    }
                }
            }


        }

    }

    private fun insertNote() {
        title = addNoteBottomSheetBinding.noteTitleET.text.toString().trim()
        note = addNoteBottomSheetBinding.noteET.text.toString().trim()

        if (inputCheck(title!!, note!!)) {
            val note = NotesModel(null, null, null, title!!, note!!, date!!)
            (activity as MainActivity).notesViewModel.insertNote(note)
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.saved_success),
                Toast.LENGTH_SHORT
            ).show()
            activity?.onBackPressed()
        } else {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.fill_out_all_fields),
                Toast.LENGTH_SHORT
            )
                .show()
        }

    }

    private fun updateNote() {
        addNoteBottomSheetBinding.apply {
            title = noteTitleET.text.toString().trim()
            note = noteET.text.toString().trim()

            if (inputCheck(title!!, note!!)) {
                val note = NotesModel(
                    noteModel?.id,
                    noteModel?.folderID,
                    noteModel?.folderName,
                    title!!,
                    note!!,
                    date!!
                )
                (activity as MainActivity).notesViewModel.updateNote(note)
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.update_success),
                    Toast.LENGTH_SHORT
                ).show()
                activity?.onBackPressed()
            } else {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.fill_out_all_fields),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun updateFolder() {
        addNoteBottomSheetBinding.apply {
            title = noteTitleET.text.toString().trim()
            if (title!!.isNotEmpty()) {
                val folder = FolderModel(folderModel?.folderId, title!!)
                (activity as MainActivity).notesViewModel.updateFolder(folder)
                folder.folderId?.let {
                    (activity as MainActivity).notesViewModel.updateFolderName(
                        folder.title,
                        it
                    )
                }
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.update_success),
                    Toast.LENGTH_SHORT
                ).show()
                activity?.onBackPressed()
            } else {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.fill_out_all_fields),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateUI(model: Any) {
        addNoteBottomSheetBinding.apply {
            if (model is NotesModel) {
                saveBtn.text = resources.getString(R.string.update)
                saveBtn.setBackgroundResource(R.drawable.update_btn_bg)
                noteTitleET.setText(noteModel?.title)
                noteET.setText(noteModel?.note)
            } else if (model is FolderModel) {
                saveBtn.text = resources.getString(R.string.update)
                saveBtn.setBackgroundResource(R.drawable.update_btn_bg)
                noteET.visibility = View.GONE
                noteTitleET.setText(folderModel?.title)
            }

        }
    }

    private fun inputCheck(title: String, note: String): Boolean {
        return (title.isNotEmpty() && note.isNotEmpty())
    }


}