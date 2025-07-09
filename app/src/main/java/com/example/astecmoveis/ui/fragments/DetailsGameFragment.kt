package com.example.astecmoveis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.astecmoveis.databinding.FragmentDetailsGameBinding
import com.example.astecmoveis.ui.viewmodel.GameViewModel
import com.example.astecmoveis.ui.viewmodel.GameViewModelFactory

class DetailsGameFragment : Fragment() {

    private var _binding: FragmentDetailsGameBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsGameFragmentArgs by navArgs()

    private val viewModel: GameViewModel by viewModels {
        GameViewModelFactory(requireActivity().application)
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

        val game = args.game


        binding.tvTitle.text = game.title
        binding.tvGenre.text = game.genre
        binding.tvDescription.text = game.description
        binding.tvDeveloper.text = game.developer
        binding.tvReleaseYear.text = game.releaseYear.toString()
        binding.tvRating.text = game.rating.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
