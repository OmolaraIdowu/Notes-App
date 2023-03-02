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
            parent.setCardBackgroundColor(root.resources.getColor(randomColor(), null))
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

    fun randomColor(): Int {
        val backgroundColors = ArrayList<Int>()
        backgroundColors.add(R.color.pink_500)
        backgroundColors.add(R.color.yellow)
        backgroundColors.add(R.color.green)
        backgroundColors.add(R.color.pink)
        backgroundColors.add(R.color.teal)
        backgroundColors.add(R.color.purple)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(backgroundColors.size)
        return backgroundColors[randomIndex]
    }
}
