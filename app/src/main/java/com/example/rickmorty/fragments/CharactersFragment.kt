package com.example.rickmorty.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickmorty.adapter.CharactersAdapter
import com.example.rickmorty.adapter.LoadMoreAdapter
import com.example.rickmorty.adapter.common.IOnClickItem
import com.example.rickmorty.databinding.CardItemCharacterBinding
import com.example.rickmorty.databinding.FragmentCharactersBinding
import com.example.rickmorty.databinding.LoadMoreIndicatorBinding
import com.example.rickmorty.models.Character
import com.example.rickmorty.service.CharacterService
import kotlinx.coroutines.*


class CharactersFragment : Fragment() {
    private val TAG = "CharactersFragment"
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CharactersAdapter
    private val characters = mutableListOf<Character>()
    private var nextPage: Int? = 2
    private var job: Job? = null
    private var isLoadingMore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        adapter = CharactersAdapter(
            characters,
            object : IOnClickItem<Character> {
                override fun onClickItem(item: Character) {
                    Log.d(TAG, item.name)
                }
            }
        ) { adapterInflater, viewGroup, attachToRoot ->
            CardItemCharacterBinding.inflate(adapterInflater, viewGroup, attachToRoot)
        }
        val footerAdapter = LoadMoreAdapter(
            listOf(0),
        ) { adapterInflater, viewGroup, attachToRoot ->
            LoadMoreIndicatorBinding.inflate(adapterInflater, viewGroup, attachToRoot)
        }
        val concatAdapter = ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(), adapter, footerAdapter)
//        concatAdapter.addAdapter(adapter)
//        concatAdapter.addAdapter(footerAdapter)
        val layoutManager = GridLayoutManager(context, 2)
        renderListCharacters()
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == characters.size && !isLoadingMore) {
                    job?.cancel()
                    job = CoroutineScope(Dispatchers.IO).launch {
                        delay(300)
                        loadMoreCharacters()
                    }
                }

                return if (concatAdapter.getItemViewType(position) == 0) {
                    2
                } else {
                    1
                }
            }
        }
        binding.rcvListCharacters.adapter = concatAdapter
        binding.rcvListCharacters.layoutManager = layoutManager
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderListCharacters() {
        CharacterService.shared.getAllCharacters { characters ->
            this.characters.addAll(0, characters.results)
            CoroutineScope(Dispatchers.Main).launch {
                adapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
                binding.rcvListCharacters.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadMoreCharacters() {
        isLoadingMore = true
        Log.d(TAG, "loadMoreCharacters")
        if (nextPage != null) {
            CharacterService.shared.getAllCharacters(page = nextPage!!) { moreCharacters ->
                val lastPrevIndex = this.characters.size
                val next = moreCharacters.info.next
                nextPage = next?.split("=")?.last()?.toInt()
                this.characters.addAll(lastPrevIndex, moreCharacters.results)
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.notifyItemRangeInserted(lastPrevIndex, moreCharacters.results.size)
                    isLoadingMore = false
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}