package com.example.astecmoveis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.astecmoveis.R
import com.example.astecmoveis.databinding.FragmentListGamesBinding
import com.example.astecmoveis.ui.adapter.GameAdapter
import com.example.astecmoveis.ui.viewmodel.GameViewModel
import com.example.astecmoveis.ui.viewmodel.GameViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListGamesFragment : Fragment() {

    private var _binding: FragmentListGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels {
        GameViewModelFactory(requireActivity().application)
    }
    private lateinit var adapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.games.collectLatest { games ->
                adapter.submitList(games)
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.fabAdd.setOnClickListener {
            val action = ListGamesFragmentDirections.actionListGamesFragmentToFormGameFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        adapter = GameAdapter(
            onItemClick = { game ->
                val action = ListGamesFragmentDirections
                    .actionListGamesFragmentToDetailsGameFragment(game)
                findNavController().navigate(action)
            },
            onTogglePlayed = { game ->
                viewModel.togglePlayedStatus(game.id, game.isPlayed)
            },
            onDeleteClick = { game ->
                viewModel.deleteGame(game)
            },
            onEditClick = { game -> // Navega para a edição
                val action = ListGamesFragmentDirections
                    .actionListGamesFragmentToFormGameFragment(game)
                findNavController().navigate(action)
            }
        )
        binding.rvGames.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGames.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}