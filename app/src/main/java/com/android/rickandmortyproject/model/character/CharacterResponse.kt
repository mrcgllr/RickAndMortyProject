package com.android.rickandmortyproject.model.character

data class CharacterResponse(
    val info: Info,
    val results: List<Result>
)