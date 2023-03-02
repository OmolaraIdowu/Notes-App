package com.swancodes.notes.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swancodes.notes.R
import com.swancodes.notes.data.local.Note
import com.swancodes.notes.databinding.FragmentListNoteBinding

class ListNoteFragment : Fragment(), ItemClickListener {

    private var _binding: FragmentListNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteViewModel>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListNoteBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        binding.fab.setOnClickListener {
            val action = ListNoteFragmentDirections.toAddNoteFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.infoButton.setOnClickListener {
            showInfoDialog()
        }
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

    private fun searchNotes() {

    }

    private fun setUpRecyclerView() {
        viewModel.getAllNotes.observe(viewLifecycleOwner) {
            recyclerView = binding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = NotesAdapter(it, this)
            binding.emptyLayout.isVisible = it.isEmpty()
        }
    }

    /*  private fun chooseLayout() {
          val layoutButton = binding.layoutButton
          when (isLinearLayoutManager) {
              true -> {
                  recyclerView.layoutManager = LinearLayoutManager(requireContext())
                  layoutButton.setImageResource(R.drawable.ic_list)
              }
              false -> {
                  recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
                  layoutButton.setImageResource(R.drawable.ic_grid)
              }
          }
          isLinearLayoutManager = !isLinearLayoutManager
      }*/

    override fun onItemClick(note: Note) {
        // Handle navigation
        val action = ListNoteFragmentDirections.toAddNoteFragment(note)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(note: Note) {
        // Handle deletion
        showDeleteDialog(note)
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
}

