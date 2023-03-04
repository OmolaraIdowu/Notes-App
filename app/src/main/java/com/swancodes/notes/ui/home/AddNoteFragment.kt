package com.swancodes.notes.ui.home

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.swancodes.notes.R
import com.swancodes.notes.data.local.Note
import com.swancodes.notes.databinding.FragmentAddNoteBinding
import com.swancodes.notes.ui.home.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteViewModel>()
    private val args by navArgs<AddNoteFragmentArgs>()

    private var note: Note? = null
    private val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveButton.setOnClickListener {
            insertDataToDatabase()
        }
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        args.currentNote?.let {
            binding.titleTextField.setText(it.title)
            binding.textDescription.setText(it.description)
            note = it
        }
    }

    private fun insertDataToDatabase() {
        val title = binding.titleTextField.text.toString()
        val description = binding.textDescription.text.toString()
        val dateFormatter = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

        if (inputCheck(title, description)) {
            if (note == null) {
                // Create Note Object
                val note = Note(0, title, description, dateFormatter.format(Date()))
                // Add data to database
                viewModel.addNote(note)
                Toast.makeText(requireContext(), "Note successfully added", Toast.LENGTH_SHORT)
                    .show()
            } else {
                note!!.id = id
                note!!.title = title
                note!!.description = description
                note!!.date = dateFormatter.format(Date())

                viewModel.updateNote(note!!)
                Toast.makeText(requireContext(), "Note successfully updated", Toast.LENGTH_SHORT)
                    .show()
            }
            // Navigate back to ListFragment
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(title: String, description: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(description))
    }
}