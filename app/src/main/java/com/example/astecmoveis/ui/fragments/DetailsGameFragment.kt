package com.example.astecmoveis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs // Importe navArgs
import com.example.astecmoveis.databinding.FragmentDetailsGameBinding
import com.example.astecmoveis.ui.viewmodel.GameViewModel
import com.example.astecmoveis.ui.viewmodel.GameViewModelFactory // Importe a Factory

class DetailsGameFragment : Fragment() {

    private var _binding: FragmentDetailsGameBinding? = null
    private val binding get() = _binding!!

    // Usando navArgs para obter os argumentos de navegação
    private val args: DetailsGameFragmentArgs by navArgs()

    // Usando uma Factory para injetar o repositório no ViewModel
    private val viewModel: GameViewModel by viewModels {
        GameViewModelFactory(requireActivity().application) // Passa o Application para a Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val game = args.game // Obtém o objeto GameEntity dos argumentos

        // Carrega os detalhes do jogo (opcional, se você já tem o objeto completo)
        // Se você precisar de dados mais recentes do DB, descomente e use o ID:
        // viewModel.getGameById(game.id)

        // Preenche a UI com os dados do jogo
        game?.let {
            binding.tvTitle.text = it.title // Verifica o ID no fragment_details_game.xml
            binding.tvGenre.text = it.genre // Verifica o ID no fragment_details_game.xml
            // Você pode adicionar mais campos aqui, como isPlayed, description, etc.
            // Exemplo: binding.tvIsPlayed.text = if (it.isPlayed) "Jogado" else "Não Jogado"
        }

        // Se você decidiu carregar do DB no onViewCreated, observe o LiveData aqui
        viewModel.currentGame.observe(viewLifecycleOwner) { loadedGame ->
            loadedGame?.let {
                // Atualize a UI com os dados mais recentes do DB
                // Se você já preencheu a UI acima com 'args.game', pode haver redundância
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}