package com.swancodes.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swancodes.notes.model.NoteItem
import java.util.UUID

class NoteViewModel : ViewModel() {

    var noteItems = MutableLiveData<MutableList<NoteItem>>()

    init {
        noteItems.value = mutableListOf()
    }

    fun addNoteItem(newNote: NoteItem) {
        noteItems.value?.add(newNote)
        noteItems.postValue(noteItems.value)
    }

    fun updateNoteItem(id: UUID, title: String, description: String) {
        val note = noteItems.value!!.find { it.id == id }!!
        note.title = title
        note.description = description
        noteItems.postValue(noteItems.value)
    }

    fun deleteNoteItem(noteItem: NoteItem) {
        noteItems.value!!.remove(noteItem)
    }
}