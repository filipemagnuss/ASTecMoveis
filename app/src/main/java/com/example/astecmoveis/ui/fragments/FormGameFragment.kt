package com.example.astecmoveis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.astecmoveis.data.local.entities.GameEntity
import com.example.astecmoveis.databinding.FragmentFormGameBinding
import com.example.astecmoveis.ui.viewmodel.GameViewModel
import com.example.astecmoveis.ui.viewmodel.GameViewModelFactory // Importe a Factory

class FormGameFragment : Fragment() {

    private var _binding: FragmentFormGameBinding? = null
    private val binding get() = _binding!!

    // Usando uma Factory para injetar o repositório no ViewModel
    private val viewModel: GameViewModel by viewModels {
        GameViewModelFactory(requireActivity().application) // Passa o Application para a Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val genre = binding.etGenre.text.toString().trim()

            if (title.isEmpty() || genre.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Cria um GameEntity a partir dos dados do formulário
                // Preencha os campos adicionais com valores padrão ou solicite ao usuário
                val newGame = GameEntity(
                    id = 0, // O Room vai gerar o ID automaticamente
                    title = title,
                    genre = genre,
                    description = "", // Valor padrão
                    developer = "",   // Valor padrão
                    releaseYear = 2024, // Valor padrão, ajuste conforme necessário
                    rating = 0.0,     // Valor padrão
                    isPlayed = false, // Valor inicial para o status de jogado
                    imageUrl = ""     // Valor padrão
                )
                viewModel.addGame(newGame)
                // Navegar de volta após salvar
                findNavController().popBackStack()
            }
        }

        // Observa mensagens do ViewModel (sucesso/erro)
        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}