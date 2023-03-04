package com.swancodes.notes.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.swancodes.notes.R
import com.swancodes.notes.data.local.Note
import com.swancodes.notes.databinding.FragmentListNoteBinding
import com.swancodes.notes.ui.home.viewModel.NoteViewModel

class ListNoteFragment : Fragment(), ItemClickListener, SearchView.OnQueryTextListener {

    private var _binding: FragmentListNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteViewModel>()
    private lateinit var recyclerView: RecyclerView
    private var isLinearLayoutManager = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        setUpRecyclerView()

        binding.fab.setOnClickListener {
            val action = ListNoteFragmentDirections.toAddNoteFragment()
            findNavController().navigate(action)
        }

        binding.searchView.setOnQueryTextListener(this)

        binding.infoButton.setOnClickListener {
            showInfoDialog()
        }
    }

    private fun setUpRecyclerView() {
        viewModel.getAllNotes.observe(viewLifecycleOwner) {
            // Set the layout manager
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = NotesAdapter(it, this)
            binding.emptyView.isVisible = it.isEmpty()
        }

        // Set the layout button click listener
        val layoutButton = binding.layoutButton
        layoutButton.setOnClickListener {
            switchLayout()
        }
    }

    private fun switchLayout() {
        val layoutButton = binding.layoutButton
        when (isLinearLayoutManager) {
            true -> {
                recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                layoutButton.setImageResource(R.drawable.ic_grid)
            }
            false -> {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                layoutButton.setImageResource(R.drawable.ic_list)
            }
        }
        isLinearLayoutManager = !isLinearLayoutManager
    }


    private fun showInfoDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.info_dialog, null)
        val githubButton = dialogView.findViewById<ImageButton>(R.id.gitHubImage)
        val twitterButton = dialogView.findViewById<ImageButton>(R.id.twitterImage)

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)

        githubButton.setOnClickListener {
            val uri = Uri.parse("https://github.com/OmolaraIdowu")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        twitterButton.setOnClickListener {
            val uri = Uri.parse("https://mobile.twitter.com/Lara_Idowuu")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showDeleteDialog(note: Note) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delete_dialog, null)
        val deleteButton = dialogView.findViewById<Button>(R.id.deleteBtn)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelBtn)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()

        deleteButton.setOnClickListener {
            viewModel.deleteNote(note)
            alertDialog.dismiss()
        }

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun searchNotes(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                recyclerView.adapter = NotesAdapter(it, this)
            }
        }
    }

    override fun onItemClick(note: Note) {
        // Handle navigation
        val action = ListNoteFragmentDirections.toAddNoteFragment(note)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(note: Note) {
        // Handle deletion
        showDeleteDialog(note)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchNotes(query)
        }
        return true
    }
}

