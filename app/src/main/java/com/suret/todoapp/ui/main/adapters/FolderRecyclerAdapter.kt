package com.suret.todoapp.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suret.todoapp.data.model.FolderModel
import com.suret.todoapp.databinding.FolderListBinding

class FolderRecyclerAdapter : RecyclerView.Adapter<FolderRecyclerAdapter.FoldersViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<FolderModel>() {
        override fun areItemsTheSame(oldItem: FolderModel, newItem: FolderModel): Boolean {
            return oldItem.folderId == newItem.folderId
        }

        override fun areContentsTheSame(oldItem: FolderModel, newItem: FolderModel): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoldersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val folderListBinding = FolderListBinding.inflate(layoutInflater, parent, false)
        return FoldersViewHolder(folderListBinding, onLongItemClickListener,onItemClickListener)
    }

    override fun onBindViewHolder(holder: FoldersViewHolder, position: Int) {
        holder.bind(differ.currentList.getOrNull(position))
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class FoldersViewHolder(
        private val folderBiding: FolderListBinding,
        setOnLongItemClickListener: ((FolderModel) -> Unit)?,
        setOnItemClickListener: ((FolderModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(folderBiding.root) {

        fun bind(folder: FolderModel?) {
            folderBiding.folderName.text = folder?.title

            folderBiding.root.setOnLongClickListener {
                folder?.let { folders ->
                    onLongItemClickListener?.invoke(folders)
                }
                return@setOnLongClickListener true
            }
            folderBiding.root.setOnClickListener {
                folder?.let { folders ->
                    onItemClickListener?.invoke(folders)
                }
            }

        }

    }

    private var onLongItemClickListener: ((FolderModel) -> Unit)? = null

    private var onItemClickListener: ((FolderModel) -> Unit)? = null


    fun setOnLongItemClickListener(listener: (FolderModel) -> Unit) {
        onLongItemClickListener = listener
    }

    fun setOnItemClickListener(listener: (FolderModel) -> Unit) {
        onItemClickListener = listener
    }

}