package com.example.rickmorty.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ConcatAdapter
import com.example.rickmorty.adapter.CharactersAdapter
import com.example.rickmorty.adapter.LoadMoreAdapter
import com.example.rickmorty.adapter.common.IOnClickItem
import com.example.rickmorty.databinding.CardItemCharacterBinding
import com.example.rickmorty.databinding.FragmentCharactersBinding
import com.example.rickmorty.databinding.LoadMoreIndicatorBinding
import com.example.rickmorty.models.Character
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
            val contentAdapter = CharactersAdapter(
                characters.results,
                object : IOnClickItem<Character> {
                    override fun onClickItem(item: Character) {
                        Log.d("SPANCoUNT", item.name)
                    }
                }
            ) { inflater, viewGroup, attachToRoot ->
                CardItemCharacterBinding.inflate(inflater, viewGroup, attachToRoot)
            }
            var footerLayoutId = 0;
            val footerAdapter = LoadMoreAdapter(listOf(0)) { inflater, viewGroup, attachToRoot ->
                val layout = LoadMoreIndicatorBinding.inflate(inflater, viewGroup, attachToRoot)
                footerLayoutId = layout.root.id
                layout
            }
            val adapter = ConcatAdapter(contentAdapter, footerAdapter)
            CoroutineScope(Dispatchers.Main).launch {
                binding.rcvListCharacters.adapter = adapter
                binding.rcvListCharacters.layoutManager = GridLayoutManager(context, 2)
                (binding.rcvListCharacters.layoutManager as GridLayoutManager)?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (adapter.getItemViewType(position)) {
                            footerLayoutId -> 1
                            else -> 2
                        }
                    }

                }
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