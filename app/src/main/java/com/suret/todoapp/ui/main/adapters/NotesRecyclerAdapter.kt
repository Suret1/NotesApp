package com.suret.todoapp.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suret.todoapp.data.model.NotesModel
import com.suret.todoapp.databinding.AllNotesListBinding

class NotesRecyclerAdapter :
    RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<NotesModel>() {
        override fun areItemsTheSame(oldItem: NotesModel, newItem: NotesModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotesModel, newItem: NotesModel): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)


    inner class NotesViewHolder(
        private val itemBinding: AllNotesListBinding,
        setOnClickItemListener: ((NotesModel) -> Unit)?
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(notesModel: NotesModel?) {
            itemBinding.noteTitle.text = notesModel?.title
            itemBinding.note.text = notesModel?.note
            itemBinding.noteDate.text = notesModel?.date
            itemBinding.folderName.text = notesModel?.folderName

            itemBinding.root.setOnLongClickListener {
                notesModel?.let { notes ->
                    onLongItemClickListener?.invoke(notes)
                }
                return@setOnLongClickListener true
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemAllNotesListBinding = AllNotesListBinding.inflate(inflater, parent, false)
        return NotesViewHolder(itemAllNotesListBinding, onLongItemClickListener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(differ.currentList.getOrNull(position))
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onLongItemClickListener: ((NotesModel) -> Unit)? = null

    fun setOnLongItemClickListener(listener: (NotesModel) -> Unit) {
        onLongItemClickListener = listener
    }
}