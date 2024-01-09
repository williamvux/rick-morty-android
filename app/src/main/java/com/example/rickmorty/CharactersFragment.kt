package com.example.rickmorty

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.adapter.CharactersAdapter
import com.example.rickmorty.databinding.CardItemCharacterBinding
import com.example.rickmorty.databinding.FragmentCharactersBinding
import com.example.rickmorty.service.CharacterService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CharactersFragment : Fragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        renderListCharacters(inflater.context)
        return binding.root
    }

    private fun renderListCharacters(context: Context) {
        CharacterService.shared.getAllCharacters { characters ->
            val adapter = CharactersAdapter(characters.results) {inflater, viewGroup, attachToRoot ->
                CardItemCharacterBinding.inflate(inflater, viewGroup, attachToRoot)
            }
            CoroutineScope(Dispatchers.Main).launch {
                binding.rcvListCharacters.adapter = adapter
                binding.rcvListCharacters.layoutManager = GridLayoutManager(context, 2)
                binding.progressBar.visibility = View.GONE
                binding.rcvListCharacters.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}