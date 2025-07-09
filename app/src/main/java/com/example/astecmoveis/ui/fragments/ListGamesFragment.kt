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
import com.example.astecmoveis.R // Importe o R para acessar os IDs
import com.example.astecmoveis.databinding.FragmentListGamesBinding
import com.example.astecmoveis.ui.adapter.GameAdapter
import com.example.astecmoveis.ui.viewmodel.GameViewModel
import com.example.astecmoveis.ui.viewmodel.GameViewModelFactory // Importe a Factory
import kotlinx.coroutines.flow.collectLatest // Usar collectLatest para StateFlow
import kotlinx.coroutines.launch

class ListGamesFragment : Fragment() {

    private var _binding: FragmentListGamesBinding? = null
    private val binding get() = _binding!!

    // Usando uma Factory para injetar o repositório no ViewModel
    private val viewModel: GameViewModel by viewModels {
        GameViewModelFactory(requireActivity().application) // Passa o Application para a Factory
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

        // Coleta o StateFlow de jogos do ViewModel e submete à lista do adaptador
        lifecycleScope.launch {
            viewModel.games.collectLatest { games -> // Usar collectLatest para StateFlow
                adapter.submitList(games)
            }
        }

        // Observa mensagens do ViewModel (sucesso/erro)
        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        // Corrigido o ID do FAB
        binding.fabAdd.setOnClickListener { // Era fabAddGame no código original
            findNavController().navigate(
                R.id.action_listGamesFragment_to_formGameFragment // Verifique o nav_graph para o ID correto
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = GameAdapter(
            onItemClick = { game ->
                val action = ListGamesFragmentDirections
                    .actionListGamesFragmentToDetailsGameFragment(game)
                findNavController().navigate(action)
            },
            onTogglePlayed = { game -> // Adicionado o callback para alternar status
                viewModel.togglePlayedStatus(game.id, game.isPlayed)
            },
            onDeleteClick = { game -> // Adicionado o callback para deletar
                viewModel.deleteGame(game)
            }
        )
        binding.rvGames.layoutManager = LinearLayoutManager(requireContext()) // Era recyclerGames
        binding.rvGames.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}