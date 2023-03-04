package com.swancodes.notes.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swancodes.notes.R
import com.swancodes.notes.data.local.Note
import com.swancodes.notes.databinding.ItemNoteBinding
import kotlin.random.Random

class NotesAdapter(
    private var noteList: List<Note>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(
        private val binding: ItemNoteBinding,
        private val listener: ItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(noteItem: Note) = with(binding) {
            textTitle.text = noteItem.title
            textDescription.text = noteItem.description
            dateText.text = noteItem.date
            root.setOnClickListener {
                listener.onItemClick(noteItem)
            }
            root.setOnLongClickListener {
                listener.onDeleteClick(noteItem)
                true
            }
            parent.setCardBackgroundColor(root.resources.getColor(noteItem.backgroundColor, null))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
            ), listener
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount() = noteList.size

}
