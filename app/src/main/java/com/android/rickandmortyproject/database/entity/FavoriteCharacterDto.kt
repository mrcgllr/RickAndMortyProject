package com.android.rickandmortyproject.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_character_dto")
data class FavoriteCharacterDto(
    @PrimaryKey val id:Int,
    val isFavorite:Boolean
)