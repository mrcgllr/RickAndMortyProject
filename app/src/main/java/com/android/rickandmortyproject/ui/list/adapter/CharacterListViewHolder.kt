package com.android.rickandmortyproject.ui.list.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.rickandmortyproject.database.entity.CharacterDto
import com.android.rickandmortyproject.databinding.ItemCharacterGridBinding
import com.android.rickandmortyproject.databinding.ItemCharacterListBinding

class CharacterListViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: CharacterDto?,
        clickCallback: (CharacterDto) -> Unit?,
        favIconClickCallback: (CharacterDto) -> Unit?
    ) {
        when (binding) {
            is ItemCharacterListBinding -> {
                binding.model = item
                binding.itemContainer.setOnClickListener {
                    item?.let {
                        clickCallback.invoke(item)
                    }
                }
                binding.favorite.setOnClickListener {
                    item?.let {
                        favIconClickCallback.invoke(it)
                    }
                }
            }

            is ItemCharacterGridBinding -> {
                binding.model = item
                binding.itemContainer.setOnClickListener {
                    item?.let {
                        clickCallback.invoke(item)
                    }
                }
                binding.favorite.setOnClickListener {
                    item?.let {
                        favIconClickCallback.invoke(it)
                    }
                }
            }
        }
    }
}