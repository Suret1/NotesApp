package com.suret.todoapp.ui.bottomsheet

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.suret.todoapp.R
import com.suret.todoapp.data.model.NotesModel
import com.suret.todoapp.databinding.AddNoteBottomSheetBinding
import com.suret.todoapp.ui.main.MainActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class AddNoteBottomSheet : BottomSheetDialogFragment() {
    private lateinit var addNoteBottomSheetBinding: AddNoteBottomSheetBinding
    private var noteModel: NotesModel? = null
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
        val check = arguments?.getBoolean("editCheck")

        addNoteBottomSheetBinding.apply {
            if (check == true) {
                updateButton()
                saveBtn.setOnClickListener {
                    updateNote()
                }
            } else {
                saveBtn.setOnClickListener {
                    insertNote()
                }
            }


        }

    }

    private fun insertNote() {
        title = addNoteBottomSheetBinding.noteTitleET.text.toString().trim()
        note = addNoteBottomSheetBinding.noteET.text.toString().trim()

        if (inputCheck(title!!, note!!)) {
            val note = NotesModel(null, title!!, note!!, date!!)
            (activity as MainActivity).notesViewModel.insertNote(note)
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.saved_note_successfully),
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
                val note = NotesModel(noteModel?.id, title!!, note!!, date!!)
                (activity as MainActivity).notesViewModel.updateNote(note)
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.update_note_successfully),
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

    private fun updateButton() {
        addNoteBottomSheetBinding.apply {
            saveBtn.text = resources.getString(R.string.update)
            saveBtn.setBackgroundResource(R.drawable.update_btn_bg)
            noteTitleET.setText(noteModel?.title)
            noteET.setText(noteModel?.note)
        }
    }

    private fun inputCheck(title: String, note: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(note))
    }


}