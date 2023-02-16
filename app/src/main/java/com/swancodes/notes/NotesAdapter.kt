package com.swancodes.notes

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.swancodes.notes.databinding.ItemNoteBinding
import com.swancodes.notes.model.NoteItem

class NotesAdapter(
    private var notes: List<NoteItem>,
    private val clickListener: NoteItemClickListener
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(
        private val binding: ItemNoteBinding,
        private val clickListener: NoteItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteItem) = with(binding) {
            textTitle.text = note.title
            textDescription.text = note.description
            itemView.setOnClickListener {
                clickListener.onEditClick(note)
            }
            editButton.setOnClickListener {
                clickListener.onEditClick(note)
            }
            deleteButton.setOnClickListener {
                clickListener.onDeleteClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
            ), clickListener
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size // to indicate how much data overall will be displayed
}

interface NoteItemClickListener {
    fun onEditClick(noteItem: NoteItem)
    fun onDeleteClick(noteItem: NoteItem)
}