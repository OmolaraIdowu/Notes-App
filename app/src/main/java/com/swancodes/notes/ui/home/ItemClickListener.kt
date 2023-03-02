package com.swancodes.notes.ui.home

import com.swancodes.notes.data.local.Note

interface ItemClickListener {
    fun onItemClick(note: Note)
    fun onDeleteClick(note: Note)
}