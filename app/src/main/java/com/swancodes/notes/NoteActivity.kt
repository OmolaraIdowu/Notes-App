package com.swancodes.notes
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swancodes.notes.databinding.ActivityNoteBinding
import com.swancodes.notes.model.NoteItem

class NoteActivity : AppCompatActivity(), NoteItemClickListener {
    private lateinit var binding: ActivityNoteBinding
    private lateinit var recyclerView: RecyclerView
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        binding.fab.setOnClickListener {
            val activityIntent = Intent(this, EditNoteActivity::class.java)
            startActivity(activityIntent)
        }
    }

    private fun setUpRecyclerView() {
        recyclerView = binding.contentNote.recyclerView

        viewModel.noteItems.observe(this) {
            recyclerView.layoutManager =
                LinearLayoutManager(this) // associate layout with the recyclerView
            recyclerView.adapter = NotesAdapter(it!!, NoteActivity())
        }
    }

    override fun onEditClick(noteItem: NoteItem) {
        EditNoteActivity().startActivity(intent)
    }

    override fun onDeleteClick(noteItem: NoteItem) {
       viewModel.deleteNoteItem(noteItem)
    }
}