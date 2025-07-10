package com.example.astecmoveis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.astecmoveis.data.local.entities.GameEntity
import com.example.astecmoveis.databinding.FragmentFormGameBinding
import com.example.astecmoveis.ui.viewmodel.GameViewModel
import com.example.astecmoveis.ui.viewmodel.GameViewModelFactory
import java.util.Calendar

class FormGameFragment : Fragment() {

    private var _binding: FragmentFormGameBinding? = null
    private val binding get() = _binding!!

    private val args: FormGameFragmentArgs by navArgs()
    private var existingGame: GameEntity? = null

    private val viewModel: GameViewModel by viewModels {
        GameViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormGameBinding.inflate(inflater, container, false)
        existingGame = args.game
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDropdowns()
        setupView()

        binding.btnSave.setOnClickListener {
            saveGame()
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDropdowns() {
        val genres = listOf("Ação", "Aventura", "RPG", "Estratégia", "Simulação", "Esportes", "Corrida")
        val genreAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genres)
        binding.actvGenre.setAdapter(genreAdapter)

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (1980..currentYear).map { it.toString() }.reversed()
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, years)
        binding.actvReleaseYear.setAdapter(yearAdapter)

        val ratings = (0..10).map { it.toString() }
        val ratingAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, ratings)
        binding.actvRating.setAdapter(ratingAdapter)
    }

    private fun setupView() {
        existingGame?.let { game ->
            binding.etTitle.setText(game.title)
            binding.actvGenre.setText(game.genre, false)
            binding.etDescription.setText(game.description)
            binding.etDeveloper.setText(game.developer)
            binding.actvReleaseYear.setText(game.releaseYear.toString(), false)
            binding.actvRating.setText(game.rating.toInt().toString(), false)
            binding.btnSave.text = "Atualizar"
        }
    }

    private fun saveGame() {
        val title = binding.etTitle.text.toString().trim()
        val genre = binding.actvGenre.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val developer = binding.etDeveloper.text.toString().trim()
        val releaseYearStr = binding.actvReleaseYear.text.toString().trim()
        val ratingStr = binding.actvRating.text.toString().trim()

        if (title.isEmpty() || genre.isEmpty() || releaseYearStr.isEmpty() || ratingStr.isEmpty()) {
            Toast.makeText(requireContext(), "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        val releaseYear = releaseYearStr.toInt()
        val rating = ratingStr.toDouble()

        if (existingGame != null) {
            // Modo de Edição
            val updatedGame = existingGame!!.copy(
                title = title,
                genre = genre,
                description = description,
                developer = developer,
                releaseYear = releaseYear,
                rating = rating
            )
            viewModel.updateGame(updatedGame)
        } else {
            // Modo de Adição
            val newGame = GameEntity(
                title = title,
                genre = genre,
                description = description,
                developer = developer,
                releaseYear = releaseYear,
                rating = rating
            )
            viewModel.addGame(newGame)
        }
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}