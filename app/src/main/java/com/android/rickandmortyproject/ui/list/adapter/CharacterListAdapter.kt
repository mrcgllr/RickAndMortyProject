package com.android.rickandmortyproject.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.android.rickandmortyproject.R
import com.android.rickandmortyproject.database.entity.CharacterDto
import com.android.rickandmortyproject.databinding.ItemCharacterGridBinding
import com.android.rickandmortyproject.databinding.ItemCharacterListBinding
import com.android.rickandmortyproject.enum.ListType

class CharacterListAdapter(
    private val itemClickCallback: ((CharacterDto) -> Unit?),
    private val favIconClickCallback: ((CharacterDto) -> Unit?)
) :
    PagingDataAdapter<CharacterDto, CharacterListViewHolder>(object :
        DiffUtil.ItemCallback<CharacterDto>() {
        override fun areItemsTheSame(oldItem: CharacterDto, newItem: CharacterDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CharacterDto, newItem: CharacterDto): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        return when (viewType) {
            VIEW_TYPE_GRID -> {
                val viewRoot =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_character_grid, parent, false)
                CharacterListViewHolder(ItemCharacterGridBinding.bind(viewRoot))
            }
            else -> {
                val viewRoot =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_character_list, parent, false)
                CharacterListViewHolder(ItemCharacterListBinding.bind(viewRoot))
            }
        }

    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickCallback, favIconClickCallback)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)?.viewType) {
            VIEW_TYPE_LIST -> VIEW_TYPE_LIST
            else -> VIEW_TYPE_GRID
        }
    }

    companion object {
        val VIEW_TYPE_LIST = ListType.LIST.id
        val VIEW_TYPE_GRID = ListType.GRID.id
    }
}