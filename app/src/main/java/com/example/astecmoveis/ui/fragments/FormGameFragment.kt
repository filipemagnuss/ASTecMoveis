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
import com.example.astecmoveis.data.local.entities.GameEntity
import com.example.astecmoveis.databinding.FragmentFormGameBinding
import com.example.astecmoveis.ui.viewmodel.GameViewModel
import com.example.astecmoveis.ui.viewmodel.GameViewModelFactory

class FormGameFragment : Fragment() {

    private var _binding: FragmentFormGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels {
        GameViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dropdown gêneros
        val genres = listOf("Ação", "Aventura", "RPG", "Estratégia", "Simulação", "Esportes")
        val genreAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genres)
        binding.actvGenre.setAdapter(genreAdapter)

        // Dropdown ano de lançamento (1980 até ano atual)
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val years = (1980..currentYear).map { it.toString() }.reversed()
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, years)
        binding.actvReleaseYear.setAdapter(yearAdapter)

        // Dropdown nota de 0 a 10
        val ratings = (0..10).map { it.toString() }
        val ratingAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, ratings)
        binding.actvRating.setAdapter(ratingAdapter)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val genre = binding.actvGenre.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val developer = binding.etDeveloper.text.toString().trim()
            val releaseYearStr = binding.actvReleaseYear.text.toString().trim()
            val ratingStr = binding.actvRating.text.toString().trim()

            when {
                title.isEmpty() -> {
                    Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    binding.etTitle.requestFocus()
                }
                genre.isEmpty() -> {
                    Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    binding.actvGenre.requestFocus()
                }
                releaseYearStr.isEmpty() -> {
                    Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    binding.actvReleaseYear.requestFocus()
                }
                ratingStr.isEmpty() -> {
                    Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    binding.actvRating.requestFocus()
                }
                else -> {
                    val releaseYear = releaseYearStr.toIntOrNull() ?: currentYear
                    val rating = ratingStr.toDoubleOrNull() ?: 0.0

                    val newGame = GameEntity(
                        id = 0,
                        title = title,
                        genre = genre,
                        description = description,
                        developer = developer,
                        releaseYear = releaseYear,
                        rating = rating,
                        isPlayed = false,
                    )

                    viewModel.addGame(newGame)
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
