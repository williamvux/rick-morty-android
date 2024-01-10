package com.example.rickmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.rickmorty.adapter.common.IOnClickItem
import com.example.rickmorty.adapter.common.RecycleViewAdapter
import com.example.rickmorty.databinding.CardItemCharacterBinding
import com.example.rickmorty.models.Character
import com.example.rickmorty.service.CharacterStatus

class CharactersAdapter(
    characters: List<Character>,
    private val onClickItem: IOnClickItem<Character>,
    viewBindingFactory: (LayoutInflater, ViewGroup, Boolean) -> CardItemCharacterBinding
): RecycleViewAdapter<
        Character,
        CardItemCharacterBinding
        >(characters, viewBindingFactory) {
    override fun bind(
        binding: CardItemCharacterBinding,
        item: Character
    ) {
        Glide.with(binding.root.context).load(item.image).into(binding.imgCharacter)
        binding.txtName.text = item.name
        binding.txtStatus.text = "Status: ${CharacterStatus[item.status]}"
        binding.root.setOnClickListener {
            onClickItem.onClickItem(item)
        }
    }
}