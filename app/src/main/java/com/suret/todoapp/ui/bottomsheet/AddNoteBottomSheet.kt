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

        addNoteBottomSheetBinding.apply {
            saveBtn.setOnClickListener {
                insertDataToDatabase()
            }
        }

    }

    private fun insertDataToDatabase() {

        val title = addNoteBottomSheetBinding.noteTitleET.text.toString()
        val note = addNoteBottomSheetBinding.noteET.text.toString()
        val currentDate = LocalDateTime.now()
        val date = currentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))

        if (inputCheck(title, note)) {
            val note = NotesModel(null, title, note, date)
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

    private fun inputCheck(title: String, desc: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(desc))
    }

}