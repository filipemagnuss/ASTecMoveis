package com.example.astecmoveis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.astecmoveis.databinding.FragmentFormGameBinding
import com.example.astecmoveis.ui.viewmodel.GameViewModel

class FormGameFragment : Fragment() {

    private var _binding: FragmentFormGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()

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

        // Exemplo de uso do botão salvar
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val genre = binding.etGenre.text.toString()
            // Chamar ViewModel para salvar jogo
            viewModel.addGame(title, genre)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
