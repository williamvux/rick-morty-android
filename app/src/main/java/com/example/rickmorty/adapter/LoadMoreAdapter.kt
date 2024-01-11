package com.example.rickmorty.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.rickmorty.adapter.common.RecycleViewAdapter
import com.example.rickmorty.databinding.LoadMoreIndicatorBinding

class LoadMoreAdapter(
    list: List<Int>,
    viewBindingFactory: (LayoutInflater, ViewGroup, Boolean) -> LoadMoreIndicatorBinding
): RecycleViewAdapter<
        Int,
        LoadMoreIndicatorBinding
        >(list, viewBindingFactory) {
    override fun bind(
        binding: LoadMoreIndicatorBinding,
        item: Int
    ) {
        Log.d("LoadMoreAdapter", item.toString())
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}