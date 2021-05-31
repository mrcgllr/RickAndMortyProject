package com.android.rickandmortyproject.ui.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.android.rickandmortyproject.database.entity.CharacterDto
import com.android.rickandmortyproject.databinding.ItemCharacterGridBinding

class CharacterGridViewHolder(private val binding: ItemCharacterGridBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharacterDto?, clickCallback: (CharacterDto) -> Unit?, favIconClickCallback: (CharacterDto) -> Unit?) {
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