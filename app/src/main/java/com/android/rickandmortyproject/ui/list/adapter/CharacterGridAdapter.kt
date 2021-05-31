package com.android.rickandmortyproject.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.android.rickandmortyproject.R
import com.android.rickandmortyproject.database.entity.CharacterDto
import com.android.rickandmortyproject.databinding.ItemCharacterGridBinding

class CharacterGridAdapter(
    private val itemClickCallback: ((CharacterDto) -> Unit?),
    private val favIconClickCallback: ((CharacterDto) -> Unit?)
) :
    PagingDataAdapter<CharacterDto, CharacterGridViewHolder>(object :
        DiffUtil.ItemCallback<CharacterDto>() {
        override fun areItemsTheSame(oldItem: CharacterDto, newItem: CharacterDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CharacterDto, newItem: CharacterDto): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterGridViewHolder {
        val viewRoot =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character_grid, parent, false)
        return CharacterGridViewHolder(ItemCharacterGridBinding.bind(viewRoot))
    }

    override fun onBindViewHolder(holder: CharacterGridViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickCallback, favIconClickCallback)
    }
}