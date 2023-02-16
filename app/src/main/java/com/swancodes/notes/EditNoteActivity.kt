package com.swancodes.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import com.swancodes.notes.databinding.ActivityEditNoteBinding
import com.swancodes.notes.model.NoteItem
import kotlin.system.exitProcess

class EditNoteActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    private val viewModel: NoteViewModel by viewModels()

    private var noteItem: NoteItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*if (noteItem != null) {
            //binding.contentEditNote.headerTitle.text = getString(R.string.edit_note)
            val editable = Editable.Factory.getInstance()
            binding.contentEditNote.titleTextField.text = editable.newEditable(noteItem!!.title)
            binding.contentEditNote.textDetails.text = editable.newEditable(noteItem!!.description)
        } else {
            //binding.contentEditNote.headerTitle.text = getString(R.string.new_note)
        }*/

        binding.contentEditNote.backArrow.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = binding.contentEditNote.titleTextField.text.toString()
        val description = binding.contentEditNote.textDetails.text.toString()
        if (noteItem == null) {
            val newNote = NoteItem(title, description)
            viewModel.addNoteItem(newNote)
        } else {
            viewModel.updateNoteItem(noteItem!!.id, title, description)
        }

        binding.contentEditNote.titleTextField.setText("")
        binding.contentEditNote.textDetails.setText("")

        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }
}