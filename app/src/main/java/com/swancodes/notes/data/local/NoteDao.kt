package com.swancodes.notes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

// Methods for accessing the database and queries to execute inside the database
@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table ORDER BY date ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(noteItem: Note)

    @Update
    suspend fun updateNote(noteItem: Note)

    @Delete
    suspend fun deleteNote(noteItem: Note)

    @Query("SELECT * FROM note_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Note>>
}