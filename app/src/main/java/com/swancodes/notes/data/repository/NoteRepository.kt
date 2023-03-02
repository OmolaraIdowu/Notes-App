package com.swancodes.notes.data.repository

import androidx.lifecycle.LiveData
import com.swancodes.notes.data.local.Note
import com.swancodes.notes.data.local.NoteDao

// Gives access to multiple data sources
class NoteRepository(private val noteDao: NoteDao) {

    val getAllNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}