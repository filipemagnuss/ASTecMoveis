package com.example.astecmoveis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.astecmoveis.databinding.FragmentListGamesBinding
import com.example.astecmoveis.ui.adapter.GameAdapter
import com.example.astecmoveis.ui.viewmodel.GameViewModel
import kotlinx.coroutines.launch

class ListGamesFragment : Fragment() {

    private var _binding: FragmentListGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()
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
            viewModel.games.collect { games ->
                adapter.submitList(games)
            }
        }

        binding.fabAddGame.setOnClickListener {
            findNavController().navigate(
                R.id.action_listGamesFragment_to_formGameFragment
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = GameAdapter(
            onItemClick = { game ->
                val action = ListGamesFragmentDirections
                    .actionListGamesFragmentToDetailsGameFragment(game)
                findNavController().navigate(action)
            }
        )
        binding.recyclerGames.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerGames.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
